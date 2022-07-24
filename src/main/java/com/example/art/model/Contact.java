package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private Deal deal;

}
