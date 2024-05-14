package com.evri.interview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierUpdateRequest {

    @NotBlank(message = "firstName can't be empty")
    private String firstName;
    @NotBlank(message = "lastName can't be empty")
    private String lastName;
    private boolean active;
}