package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    private String designation;

    @Column(nullable = false)
    private String password;

    private Boolean isActive;

    private String roles;

    @ManyToMany(mappedBy = "coOwners")
    private List<Deal> coOwnedDeals;

    @OneToMany(mappedBy = "owner")
    private List<Deal> ownedDeals;

    public void addDeal(Deal deal){
        this.coOwnedDeals.add(deal);
        deal.getCoOwners().add(this);
    }

}
