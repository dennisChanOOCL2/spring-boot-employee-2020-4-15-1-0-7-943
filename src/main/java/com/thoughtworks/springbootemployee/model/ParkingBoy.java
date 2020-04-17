package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "parking_boy")
public class ParkingBoy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;

    @JsonIgnore
    @OneToOne(mappedBy = "parkingBoy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;
}
