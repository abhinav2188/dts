package com.example.art.model.abstracts;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Timestamps {

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

    @PrePersist
    private void onCreate(){
        createTimestamp = updateTimestamp = new Date();
    }

    @PostUpdate
    private void onUpdate(){
        updateTimestamp = new Date();
    }

}
