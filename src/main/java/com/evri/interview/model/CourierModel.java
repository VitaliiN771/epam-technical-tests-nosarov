package com.evri.interview.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourierModel {

    private long id;
    private String name;
    private boolean active;
}