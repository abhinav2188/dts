package com.example.art.model.abstracts;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class Timestamps {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

}
