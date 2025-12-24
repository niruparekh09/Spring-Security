package com.nrv.SpringSecurityV2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "MotoGP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MotoGP {
    @Id
    private Integer number;
    private String name;
    private String manufacturer;
    private String bike;
    private String acronym;
    private String wc;
}
