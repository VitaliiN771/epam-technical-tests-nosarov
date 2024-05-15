package com.evri.interview.controller;

import com.evri.interview.model.CourierModel;
import com.evri.interview.model.CourierUpdateRequest;
import com.evri.interview.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierController {

    private CourierService courierService;

    @GetMapping
    public ResponseEntity<List<CourierModel>> getAllCouriers(
            @RequestParam(name = "isActive", required = false, defaultValue = "false") boolean isActive) {
        return ResponseEntity.ok(courierService.getAllCouriers(isActive));
    }

    @PutMapping("/{courierId}")
    public ResponseEntity<CourierModel> updateCourier(@PathVariable final long courierId,
                                                      @Valid @RequestBody CourierUpdateRequest courier) {
        return ResponseEntity.ok(courierService.update(courierId, courier));
    }
}