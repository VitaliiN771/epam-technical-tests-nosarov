package com.evri.interview.controller;

import com.evri.interview.exception.NotFoundException;
import com.evri.interview.model.CourierModel;
import com.evri.interview.model.CourierUpdateRequest;
import com.evri.interview.service.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.evri.interview.TestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourierController.class)
class CourierControllerTest {

    private static final String URL = "/api/v1/couriers";
    private static final String IS_ACTIVE = "isActive";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourierService courierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getShouldReturnAllActiveCouriers_WhenIsActiveTrue() throws Exception {
        List<CourierModel> couriers = getActiveCouriersModels();
        when(courierService.getAllCouriers(true)).thenReturn(couriers);
        String couriersJson = objectMapper.writeValueAsString(couriers);

        mockMvc.perform(get(URL)
                        .param(IS_ACTIVE, Boolean.TRUE.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(couriersJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(courierService, times(1)).getAllCouriers(true);
    }

    @Test
    void getShouldReturnAllInactiveCouriers_WhenIsActiveFalse() throws Exception {
        List<CourierModel> couriers = getInactiveCouriersModels();
        when(courierService.getAllCouriers(false)).thenReturn(couriers);
        String couriersJson = objectMapper.writeValueAsString(couriers);

        mockMvc.perform(get(URL)
                        .param(IS_ACTIVE, Boolean.FALSE.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(couriersJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(courierService, times(1)).getAllCouriers(false);
    }

    @SneakyThrows
    @Test
    void getShouldReturnAllInactiveCouriers_WhenIsActiveDefault() {
        List<CourierModel> couriers = getInactiveCouriersModels();
        when(courierService.getAllCouriers(false)).thenReturn(couriers);
        String couriersJson = objectMapper.writeValueAsString(couriers);

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(couriersJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(courierService, times(1)).getAllCouriers(false);
    }

    @SneakyThrows
    @Test
    void putShouldReturnStatusNotFound_WhenCourierByIdNotExist() {
        long courierId = 10L;
        CourierUpdateRequest courier = CourierUpdateRequest.builder()
                .firstName("Ann")
                .lastName("Smith")
                .active(true)
                .build();
        when(courierService.update(courierId, courier)).thenThrow(new NotFoundException());

        mockMvc.perform(put(URL + "/" + courierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courier)))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void putShouldUpdateCourier_IfExist() {
        long courierId = 10L;
        CourierModel courier = CourierModel.builder()
                .id(courierId)
                .name("Ann Smith")
                .active(true)
                .build();
        CourierUpdateRequest updateRequest = CourierUpdateRequest.builder()
                .firstName("Ann")
                .lastName("Smith")
                .active(true).build();
        when(courierService.update(courierId, updateRequest)).thenReturn(courier);

        mockMvc.perform(put(URL + "/" + courierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(courier)));
        verify(courierService, times(1)).update(courierId, updateRequest);
    }

    @SneakyThrows
    @Test
    void putShouldReturnStatusNotValid_IfRequestIsWrong() {
        long courierId = 10L;
        CourierUpdateRequest updateRequest = CourierUpdateRequest.builder()
                .firstName("Ann")
                .lastName("")
                .active(true).build();
        when(courierService.update(courierId, updateRequest)).thenReturn(CourierModel.builder().build());
        mockMvc.perform(put(URL + "/" + courierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
        verify(courierService, times(0)).update(courierId, updateRequest);
    }
}