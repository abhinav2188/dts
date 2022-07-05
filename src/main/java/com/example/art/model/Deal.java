package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.enums.DealStage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Deal extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date openingDate;

    private String siteLocation;

    private String productDetails;

    @Enumerated(EnumType.STRING)
    private DealStage dealStage;

    private String workType;

    private String subCategory;

    private String roadDetails;

    private String cateredByVertical;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> coOwnerList;

    private Integer expectedNumberOfDays;

    @Temporal(TemporalType.DATE)
    private Date expectedCloseDate;

    @Temporal(TemporalType.DATE)
    private Date actualCloseDate;

    private Double dealValueInCr;

    private String paymentTerms;

    private String paymentType;

    private Double paymentFactor;

    private String unitOfQuantity;

    private String orderSizeFactor;

    private String lastPurchaseDetails;

    private String CompetitorsInfo;

    private Integer ownerFocus;

    private Double dealProbability;

    private Long expectedTurnover;

    private Long proximityFromBase;

    @ManyToOne
    @JoinColumn
    private Party party;

    @OneToMany(mappedBy = "deal")
    private List<Interaction> interactionList;

    @OneToMany(mappedBy = "deal")
    private List<Contact> contactList;

}
