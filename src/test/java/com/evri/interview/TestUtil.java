package com.evri.interview;

import com.evri.interview.model.CourierModel;
import com.evri.interview.repository.entity.CourierEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    public static List<CourierModel> getAllCourierModels() {
        return Arrays.asList(
                CourierModel.builder().id(1).name("John Random").active(true).build(),
                CourierModel.builder().id(2).name("Sam Kin").active(true).build(),
                CourierModel.builder().id(3).name("Vitalii Nosarov").active(false).build(),
                CourierModel.builder().id(4).name("Ihor Jonson").active(false).build()
        );
    }

    public static List<CourierEntity> getAllCouriersEntities() {
        return Arrays.asList(
                CourierEntity.builder().id(1).firstName("John").lastName("Random").active(true).build(),
                CourierEntity.builder().id(2).firstName("Sam").lastName("Kin").active(true).build(),
                CourierEntity.builder().id(3).firstName("Vitalii").lastName("Nosarov").active(false).build(),
                CourierEntity.builder().id(4).firstName("Ihor").lastName("Jonson").active(false).build()
        );
    }

    public static List<CourierModel> getActiveCouriersModels() {
        return getAllCourierModels().stream().filter(CourierModel::isActive).collect(Collectors.toList());
    }

    public static List<CourierModel> getInactiveCouriersModels() {
        return getAllCourierModels().stream().filter(courier -> !courier.isActive()).collect(Collectors.toList());
    }

    public static List<CourierEntity> getActiveCouriersEntities() {
        return getAllCouriersEntities().stream().filter(CourierEntity::isActive).collect(Collectors.toList());
    }

    public static List<CourierEntity> getInactiveCouriersEntities() {
        return getAllCouriersEntities().stream().filter(courier -> !courier.isActive()).collect(Collectors.toList());
    }
}
