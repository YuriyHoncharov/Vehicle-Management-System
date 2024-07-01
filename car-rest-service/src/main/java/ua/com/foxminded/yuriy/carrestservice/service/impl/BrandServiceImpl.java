package ua.com.foxminded.yuriy.carrestservice.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.Model;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDtoPage;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityAlreadyExistException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.EntityNotFoundException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.FilterIllegalArgumentException;
import ua.com.foxminded.yuriy.carrestservice.exception.customexception.ValidationException;
import ua.com.foxminded.yuriy.carrestservice.repository.BrandRepository;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.utils.mapper.BrandConverter;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

  private BrandRepository brandRepository;
  private BrandConverter brandConverter;
  private ModelRepository modelRepository;

  public BrandServiceImpl(BrandRepository brandRepository, BrandConverter brandConverter,
      ModelRepository modelRepository) {
    this.brandRepository = brandRepository;
    this.brandConverter = brandConverter;
    this.modelRepository = modelRepository;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    brandRepository.deleteById(id);
  }

  @Override
  public BrandDto getDtoById(Long id) {
    return brandConverter.convertToDto(brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Entity with following ID not found : " + id.toString())));
  }

  @Override
  @Transactional
  public BrandDto save(@Valid BrandPostDto brand) {
    if (checkIfBrandExists(brand.getName())) {
      throw new EntityAlreadyExistException("Brand with following name already exists : " + brand.getName());
    }

    Set<Long> notAddedModelIds = new HashSet<>();
    Brand newBrand = new Brand();
    newBrand.setName(brand.getName());

    if (brand.getModels() != null) {
      notAddedModelIds = notAvailableModelsForAdd(brand.getModels(), brand.getName());
      Set<Model> models = availableModelsForAdd(brand.getModels(), brand.getName());
      models.forEach(model -> model.setBrand(newBrand));
      newBrand.setModels(models);
    }

    BrandDto savedBrand = brandConverter.convertToDto(brandRepository.save(newBrand));
    if (!notAddedModelIds.isEmpty()) {
      log.info("Models with the following IDs were not added: {}", notAddedModelIds);
      throw new ValidationException("Models with the following IDs were not added: " + notAddedModelIds);
    }
    return savedBrand;
  }

  @Override
  @Transactional
  public BrandDto update(@Valid BrandPutDto brand) {

    Brand brandToUpdate = getById(brand.getId());
    Optional<Brand> existingBrandWithName = brandRepository.findByName(brand.getName());
    Set<Long> notAddedModelIds = new HashSet<>();

    if (existingBrandWithName.isPresent() && !existingBrandWithName.get().getId().equals(brand.getId())) {
      throw new EntityAlreadyExistException(
          "Brand with the name '" + brand.getName() + "' already exists with a different ID.");
    }
    if (!brandToUpdate.getName().equals(brand.getName())) {
      brandToUpdate.setName(brand.getName());
    }
    if (brand.getModels() != null) {
      Set<Model> models = availableModelsForAdd(brand.getModels(), brand.getName());
      models.forEach(model -> model.setBrand(brandToUpdate));
      brandToUpdate.setModels(models);
    }
    BrandDto brandDto = brandConverter.convertToDto(brandRepository.save(brandToUpdate));
    if (!notAddedModelIds.isEmpty()) {
      log.info("Models with the following IDs were not added: {}", notAddedModelIds);
      throw new ValidationException("Models with the following IDs were not added: " + notAddedModelIds);
    }
    return brandDto;
  }

  private boolean checkIfBrandExists(String name) {
    log.info("Entering checkIfBrandExists with following name param : {}", name);
    boolean exist = brandRepository.findByName(name).isPresent();
    if (exist) {
      log.debug("Brand with following name : {}, exists in db.", name);
      return exist;
    } else {
      log.info("Brand with following name : {}, does not exists in db.", name);
      return false;
    }
  }

  @Override
  public Brand getById(Long id) {
    return brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand with following ID not found : " + id.toString()));
  }

  @Override
  @Transactional
  public Set<Brand> saveAll(Set<Brand> brands) {
    return new HashSet<>(brandRepository.saveAll(brands));
  }

  @Override
  public BrandDtoPage getAll(Pageable pageable) {
    log.info("Calling getAll() with following pagealbe param : page - {}, sort - {}, size - {}  ",
        pageable.getPageNumber(), pageable.getSort(), pageable.getPageSize());
    try {
      return brandConverter.convertToPageDto(brandRepository.findAll(pageable));
    } catch (Exception e) {
      log.error("Illegal argument for pagination : {}", pageable.toString());
      throw new FilterIllegalArgumentException("Illegal argument for pagination : " + pageable.toString());
    }
  }

  public Set<Long> notAvailableModelsForAdd(List<Long> models, String brandName) {
    if (models == null) {
      return Collections.emptySet();
    }
    return models.stream().filter(modelId -> {
      Optional<Model> optionalModel = modelRepository.findById(modelId);
      if (optionalModel.isPresent()) {
        Model model = optionalModel.get();
        return model.getBrand() == null || !model.getBrand().getName().equals(brandName);
      } else {
        return true;
      }
    }).collect(Collectors.toSet());
  }

  public Set<Model> availableModelsForAdd(List<Long> models, String brandName) {
    if (models == null) {
      return Collections.emptySet();
    }
    return models.stream().map(modelId -> {
      Optional<Model> optionalModel = modelRepository.findById(modelId);
      if (optionalModel.isPresent()) {
        Model model = optionalModel.get();
        if (model.getBrand() == null || model.getBrand().getName().equals(brandName)) {
          return model;
        }
      }
      return null;
    }).filter(Objects::nonNull).collect(Collectors.toSet());
  }
}
