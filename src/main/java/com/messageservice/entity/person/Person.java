package com.messageservice.entity.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.messageservice.entity.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String fiscalCodeNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_details_id")
    private PersonDetails personDetails;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "person_team",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn (name = "team_id"))
    private Set<Team> teams = new HashSet<>();

}
