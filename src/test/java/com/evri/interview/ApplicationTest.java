package com.evri.interview;

import com.evri.interview.model.CourierModel;
import com.evri.interview.model.CourierUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.evri.interview.TestUtil.getActiveCouriersModels;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "http://localhost:" + port + "/api/v1/couriers";
    private static final String IS_ACTIVE = "isActive";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void getShouldReturnAllActiveCouriers_WhenIsActiveTrue() {
        List<CourierModel> inactiveCouriers = getActiveCouriersModels();
        mockMvc.perform(
                        get(URL).param(IS_ACTIVE, Boolean.TRUE.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(inactiveCouriers)));
    }

    @SneakyThrows
    @Test
    void putShouldUpdateCourier_IfExist() {
        long courierId = 3L;
        CourierModel expectedCourier = CourierModel.builder()
                .id(courierId)
                .name("Vitalii Nosarov")
                .active(true)
                .build();
        CourierUpdateRequest updateRequest = CourierUpdateRequest.builder()
                .firstName("Vitalii")
                .lastName("Nosarov")
                .active(true)
                .build();
        mockMvc.perform(put(URL + "/" + courierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCourier)));
    }
}