package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.enums.DealStage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Deal extends Timestamps {

    // initial entry

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private Party party;

    // product details - section 2

    private String productType;

    private String subCategoryProduct;

    private String unitOfQuantity;

    private String orderSizeFactor;

    private String typeOfWork;

    private String roadDetails;

    // deal details - section 3

    private String siteLocation;

    private String cateredByVertical;

    private String paymentType;

    @Temporal(TemporalType.DATE)
    private Date openingDate;

    @Temporal(TemporalType.DATE)
    private Date expectedCloseDate;

    @Temporal(TemporalType.DATE)
    private Date actualCloseDate;

    private Integer expectedNumberOfDays;

    private String expectedDeliveryAddress;

    private String lastPurchaseDetails;

    private String competitorsInfo;

    private String remarks;

    // additional details - section 4

    @Enumerated(EnumType.STRING)
    private DealStage dealStage;

    private Boolean isActive;

    private Double dealValueInCr;

    private String paymentTerms;

    private Double paymentFactor;

    private Integer ownerFocus;

    private Double dealProbability;

    private String expectedTurnover;

    private String proximityFromBase;

    // deal section 5

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User owner;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "deals_co_owners")
    private List<User> coOwners;

    @JsonIgnore
    @OneToMany(mappedBy = "deal")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Interaction> interactions;

    @JsonIgnore
    @OneToMany(mappedBy = "deal")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Contact> contacts;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "deal")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Document> attachments;

    public void removeUser(User user) {
        this.getCoOwners().remove(user);
    }

    // todo : brochures

}
