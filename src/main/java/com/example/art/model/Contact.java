package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.enums.ContactType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Contact extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String designation;

    @Enumerated(EnumType.STRING)
    private ContactType type;

    @ManyToOne
    @JoinColumn
    private Deal deal;

    @ManyToMany
    private List<Interaction> interactionList;

}
