package com.example.art.model;

import com.example.art.model.enums.DealStage;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Interaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date interactionDate;

    @Enumerated(EnumType.STRING)
    private DealStage dealStage;

    private String meetingLocation;

    private String description;

    @ManyToMany
    private List<Contact> meetingPersons;

    // bidirectional
    @ManyToOne
    @JoinColumn
    private Deal deal;

    // bidirectional
    @ManyToOne
    @JoinColumn
    private User handler;

}
