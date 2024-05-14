package com.evri.interview.converter;

import com.evri.interview.model.CourierModel;
import com.evri.interview.repository.entity.CourierEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityToModelConverterTest {

    @Test
    void shouldConvertEntityToModelCorrectly() {
        CourierEntity entity = new CourierEntity();
        entity.setId(1);
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setActive(true);

        EntityToModelConverter entityToModelConverter = new EntityToModelConverter();
        CourierModel resultModel = entityToModelConverter.convert(entity);

        assertEquals(1, resultModel.getId());
        assertEquals("John Doe", resultModel.getName());
        assertTrue(resultModel.isActive());
    }
}