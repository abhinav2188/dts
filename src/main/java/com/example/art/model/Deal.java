package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.views.DealCardView;
import com.example.art.model.views.DealExcelView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedNativeQuery(name = "Deal.findAllDealExcelView_Named",
        query = "SELECT d.id, d.name as deal_name , p.party_name as party_name," +
                " group_concat(u.email separator ',') as co_owners " +
                "from (user u join deal_co_owners dco on u.id=dco.co_owners_id)" +
                " join deal d on d.id = dco.co_owned_deals_id join party p on p.id = d.party_id " +
                "group by d.name order by d.name",
        resultSetMapping = "Mapping.DealExcelView")
@SqlResultSetMapping(name = "Mapping.DealExcelView",
        classes = @ConstructorResult(targetClass = DealExcelView.class,
                columns = {@ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "deal_name", type=String.class),
                        @ColumnResult(name = "party_name", type=String.class),
                        @ColumnResult(name = "co_owners", type=String.class)
                }))
@NamedNativeQuery(name = "Deal.findAllDealCardView_Named",
        query = "SELECT d.id as deal_id, d.create_timestamp, d.update_timestamp," +
                " d.name as deal_name , p.party_name as party_name, p.id as party_id," +
                " d.deal_stage, d.opening_date, d.is_active," +
                " group_concat(u.email separator ',') as co_owners " +
                "from (user u join deal_co_owners dco on u.id=dco.co_owners_id) " +
                "join deal d on d.id = dco.co_owned_deals_id " +
                "join party p on p.id = d.party_id group by d.name " +
                "order by d.update_timestamp " +
                "limit :pageSize ;",
        resultSetMapping = "Mapping.DealCardView")
@SqlResultSetMapping(name = "Mapping.DealCardView",
        classes = @ConstructorResult(targetClass = DealCardView.class,
                columns = {@ColumnResult(name = "deal_id", type=Long.class),
                        @ColumnResult(name = "create_timestamp", type=Date.class),
                        @ColumnResult(name = "update_timestamp", type=Date.class),
                        @ColumnResult(name = "deal_name", type=String.class),
                        @ColumnResult(name = "party_name", type=String.class),
                        @ColumnResult(name = "party_id", type=String.class),
                        @ColumnResult(name = "deal_stage", type=String.class),
                        @ColumnResult(name = "opening_date", type=Date.class),
                        @ColumnResult(name = "is_active", type=Boolean.class),
                        @ColumnResult(name = "co_owners", type=String.class)
                }))
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

    @Temporal(TemporalType.DATE)
    private Date nfud;

    private Integer expectedNumberOfDays;

    private String expectedDeliveryAddress;

    private String lastPurchaseDetails;

    private String competitorsInfo;

    private String remarks;

    // additional details - section 4

    private String dealStage;

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
