package com.example.art.model;

import com.example.art.model.enums.DropdownType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="dropdowns")
public class DropdownValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private Integer valueOrder;

    @Enumerated(EnumType.STRING)
    private DropdownType dropdownType;

}
