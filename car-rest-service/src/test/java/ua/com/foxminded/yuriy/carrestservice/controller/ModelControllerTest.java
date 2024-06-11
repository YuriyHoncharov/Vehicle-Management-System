package ua.com.foxminded.yuriy.carrestservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.yuriy.carrestservice.entities.Brand;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.modelDto.ModelPutDto;
import ua.com.foxminded.yuriy.carrestservice.repository.ModelRepository;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;
import ua.com.foxminded.yuriy.carrestservice.service.ModelService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {
	 @Mock
	 private BrandService brandService;
	 @Mock
	 private ModelRepository modelRepository;
    @Mock
    private ModelService modelService;
    @InjectMocks
    private ModelController modelController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(modelController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void save_shouldSaveNewModel() throws Exception {
        ModelPostDto modelPostDto = new ModelPostDto();
        modelPostDto.setName("Test Model");
        modelPostDto.setBrandId(1L);
        Brand brand = new Brand();
        brand.setId(1L);
        ModelDto modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setName("Test Model");
        
        when(modelService.save(modelPostDto)).thenReturn(modelDto);
        
        mockMvc.perform(post("/api/v1/model")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(modelDto.getId()))
                .andExpect(jsonPath("$.name").value(modelDto.getName()));
    }

    @Test
    void update_shouldUpdateModelInfo() throws Exception {
        ModelPutDto modelPutDto = new ModelPutDto();
        modelPutDto.setId(1L);
        modelPutDto.setName("Updated Model");
        modelPutDto.setBrandId(1L);

        ModelDto modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setName("Updated Model");

        when(modelService.update(modelPutDto)).thenReturn(modelDto);

        mockMvc.perform(put("/api/v1/model")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelPutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(modelDto.getId()))
                .andExpect(jsonPath("$.name").value(modelDto.getName()));
    }

    @Test
    void delete_shouldDeleteModelSuccessfully() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/model/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void get_shouldReturnModelById() throws Exception {
        Long id = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setId(id);
        modelDto.setName("Test Model");

        when(modelService.getDtoById(id)).thenReturn(modelDto);

        mockMvc.perform(get("/api/v1/model/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(modelDto.getId()))
                .andExpect(jsonPath("$.name").value(modelDto.getName()));
    }
}
