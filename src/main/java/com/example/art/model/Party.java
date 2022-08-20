package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Party extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String partyName;

    private String address;

    private String authority;

    private String mobile;

    private String email;

    private Boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "party", cascade = CascadeType.DETACH)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Deal> deals;

    public Party addDeal(Deal deal){
        this.deals.add(deal);
        deal.setParty(this);
        return this;
    }

}
