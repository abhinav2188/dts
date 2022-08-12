package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Interaction extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date meetingDate;

    private String dealStage;

    private String meetingLocation;

    private String meetingDetails;

    private String contacts;

    private String consultants;

    private String handlers;

    // bidirectional
    @ManyToOne
    @JoinColumn
    private Deal deal;

}
