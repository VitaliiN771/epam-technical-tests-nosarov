package com.evri.interview.converter;

import com.evri.interview.model.CourierModel;
import com.evri.interview.repository.entity.CourierEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToModelConverter implements Converter<CourierEntity, CourierModel> {

    @Override
    public CourierModel convert(CourierEntity entity) {
        return CourierModel.builder()
                .id(entity.getId())
                .name(String.format("%s %s", entity.getFirstName(), entity.getLastName()))
                .active(entity.isActive())
                .build();
    }
}