package com.messageservice.entity.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PersonDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String phoneNumber;
    private String email;
    private Integer age;
    @OneToOne(mappedBy = "personDetails")
    @JsonIgnore
    private Person person;
    @Embedded
    private Company company;

    public PersonDetails(int id, String phoneNumber, String email, Integer age, Company company) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.company = company;
    }
}
