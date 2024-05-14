package com.evri.interview.service;

import com.evri.interview.exception.NotFoundException;
import com.evri.interview.model.CourierModel;
import com.evri.interview.model.CourierUpdateRequest;
import com.evri.interview.repository.CourierRepository;
import com.evri.interview.repository.entity.CourierEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository repository;
    private final ConversionService conversionService;

    public List<CourierModel> getAllCouriers(boolean active) {
        return repository.findByActive(active)
                .stream()
                .map(courier -> conversionService.convert(courier, CourierModel.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public CourierModel update(long courierId, CourierUpdateRequest courierUpdateRequest) {
        CourierEntity updatedCourier = repository.findById(courierId)
                .map(courierEntity -> {
                    courierEntity.setFirstName(courierUpdateRequest.getFirstName());
                    courierEntity.setLastName(courierUpdateRequest.getLastName());
                    courierEntity.setActive(courierUpdateRequest.isActive());
                    return repository.save(courierEntity);
                })
                .orElseThrow(() -> new NotFoundException(
                        String.format("Courier with id %s not found", courierId)));
        return conversionService.convert(updatedCourier, CourierModel.class);
    }
}