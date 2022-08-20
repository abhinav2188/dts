package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Document extends Timestamps {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String documentName;

    private String documentType;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn
    private Deal deal;

}
