package com.springconcepts.usermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<Address> address;
    private Boolean active;
}
