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
    private Date meetingDate;

    @Enumerated(EnumType.STRING)
    private DealStage dealStage;

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
