package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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

}
