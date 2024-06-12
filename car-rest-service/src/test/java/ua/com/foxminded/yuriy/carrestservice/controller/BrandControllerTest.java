package ua.com.foxminded.yuriy.carrestservice.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPostDto;
import ua.com.foxminded.yuriy.carrestservice.entities.dto.brandDto.BrandPutDto;
import ua.com.foxminded.yuriy.carrestservice.service.BrandService;

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
class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @InjectMocks
    private BrandController brandController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void save_shouldSaveNewBrand() throws Exception {
        BrandPostDto brandPostDto = new BrandPostDto();
        brandPostDto.setName("Test Brand");

        BrandDto brandDto = new BrandDto();
        brandDto.setId(1L);
        brandDto.setName("Test Brand");

        when(brandService.save(brandPostDto)).thenReturn(brandDto);

        mockMvc.perform(post("/api/v1/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(brandDto.getId()))
                .andExpect(jsonPath("$.name").value(brandDto.getName()));
    }

    @Test
    void update_shouldUpdateBrandInfo() throws Exception {
        BrandPutDto brandPutDto = new BrandPutDto();
        brandPutDto.setId(1L);
        brandPutDto.setName("Updated Brand");

        BrandDto brandDto = new BrandDto();
        brandDto.setId(1L);
        brandDto.setName("Updated Brand");

        when(brandService.update(brandPutDto)).thenReturn(brandDto);

        mockMvc.perform(put("/api/v1/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandPutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(brandDto.getId()))
                .andExpect(jsonPath("$.name").value(brandDto.getName()));
    }

    @Test
    void delete_shouldDeleteBrandSuccessfully() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/v1/brand/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void get_shouldReturnBrandById() throws Exception {
        Long id = 1L;
        BrandDto brandDto = new BrandDto();
        brandDto.setId(id);
        brandDto.setName("Test Brand");
        
        when(brandService.getDtoById(id)).thenReturn(brandDto);
        
        mockMvc.perform(get("/api/v1/brand/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(brandDto.getId()))
                .andExpect(jsonPath("$.name").value(brandDto.getName()));
    }
}
