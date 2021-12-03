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
    public String userId;

    public String firstName;

    public String lastName;

    public LocalDate birthDate;

    public List<Address> address;
}
