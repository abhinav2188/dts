package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String designation;

    @ManyToMany
    private List<Deal> dealList;

    @OneToMany(mappedBy = "handler")
    private List<Interaction> interactionList;

    @OneToMany(mappedBy = "owner")
    private List<Deal> ownedDealList;

}
