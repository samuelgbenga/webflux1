package com.example.reactiveprog1;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class UserRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private Boolean isPreferred;
    private LocalDate birthDate;
}
