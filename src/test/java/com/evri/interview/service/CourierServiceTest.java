package com.evri.interview.service;

import com.evri.interview.exception.NotFoundException;
import com.evri.interview.model.CourierModel;
import com.evri.interview.model.CourierUpdateRequest;
import com.evri.interview.repository.CourierRepository;
import com.evri.interview.repository.entity.CourierEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.evri.interview.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CourierServiceTest {

    @Mock
    private CourierRepository repository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private CourierService courierService;

    @Test
    void shouldReturnAllActiveCouriers_whenActiveIsTrue() {
        List<CourierEntity> courierEntities = getActiveCouriersEntities();
        List<CourierModel> expectedCouriers = getActiveCouriersModels();
        when(repository.findByActive(true)).thenReturn(courierEntities);
        when(conversionService.convert(courierEntities.get(0), CourierModel.class)).thenReturn(expectedCouriers.get(0));
        when(conversionService.convert(courierEntities.get(1), CourierModel.class)).thenReturn(expectedCouriers.get(1));

        List<CourierModel> actualCouriersList = courierService.getAllCouriers(true);

        assertEquals(expectedCouriers, actualCouriersList);
        verify(repository, times(1)).findByActive(true);
        verify(repository, times(0)).findAll();
    }

    @Test
    void shouldReturnAllInactiveCouriers_whenActiveIsFalse() {
        List<CourierEntity> courierEntities = getInactiveCouriersEntities();
        List<CourierModel> expectedCouriers = getInactiveCouriersModels();
        when(repository.findByActive(false)).thenReturn(courierEntities);
        when(conversionService.convert(courierEntities.get(0), CourierModel.class)).thenReturn(expectedCouriers.get(0));
        when(conversionService.convert(courierEntities.get(1), CourierModel.class)).thenReturn(expectedCouriers.get(1));

        List<CourierModel> actualCouriersList = courierService.getAllCouriers(false);

        assertEquals(expectedCouriers, actualCouriersList);
        verify(repository, times(1)).findByActive(false);
        verify(repository, times(0)).findAll();
    }

    @Test
    void shouldThrowNotFoundException_ifCourierNotFound() {
        long courierId = 10L;
        CourierUpdateRequest updateRequest = CourierUpdateRequest.builder()
                .firstName("Ann")
                .lastName("Smith")
                .active(true)
                .build();
        when(repository.findById(courierId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courierService.update(courierId, updateRequest));
        verify(repository, times(0)).save(any());
    }

    @Test
    void shouldUpdateCourier_ifCourierFound() {
        long courierId = 10L;
        CourierUpdateRequest updateRequest = CourierUpdateRequest.builder()
                .firstName("Ann")
                .lastName("Smith")
                .active(true)
                .build();
        CourierEntity oldEntity = CourierEntity.builder()
                .firstName("Annet")
                .lastName("Jordan")
                .active(false)
                .build();
        CourierModel expectedModel = CourierModel.builder()
                .name("Ann Smith")
                .active(true)
                .build();
        CourierEntity expectedEntity = CourierEntity.builder()
                .firstName("Ann")
                .lastName("Smith")
                .active(true)
                .build();
        when(repository.findById(courierId)).thenReturn(Optional.ofNullable(oldEntity));
        when(conversionService.convert(expectedEntity, CourierModel.class)).thenReturn(expectedModel);
        when(repository.save(expectedEntity)).thenReturn(expectedEntity);

        CourierModel actualModel = courierService.update(courierId, updateRequest);

        verify(repository, times(1)).findById(courierId);
        verify(repository, times(1)).save(expectedEntity);
        assertEquals(expectedModel, actualModel, "Wrong return value");
    }
}