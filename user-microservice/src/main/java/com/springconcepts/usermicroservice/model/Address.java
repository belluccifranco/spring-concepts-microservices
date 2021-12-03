package com.springconcepts.usermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private AddressType addressType;

    private String streetName;

    private String streetNumber;

}
