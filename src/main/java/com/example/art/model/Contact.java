package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.views.ContactExcelView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@NamedNativeQuery(name = "Contact.findAllContactExcelViews_Named",
        query = "select c.id, c.create_timestamp, c.update_timestamp, c.name, " +
                "c.email1, c.email2, c.mobile1, c.mobile2, c.designation," +
                "c.address, c.deal_id, d.name as deal_name " +
                "from contact c join deal d on c.deal_id = d.id order by c.update_timestamp desc",
        resultSetMapping = "Mapping.ContactExcelView")
@SqlResultSetMapping(name = "Mapping.ContactExcelView",
        classes = @ConstructorResult(targetClass = ContactExcelView.class,
                columns = {
                @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "create_timestamp", type=Date.class),
                        @ColumnResult(name = "update_timestamp", type=Date.class),
                        @ColumnResult(name = "name", type=String.class),
                        @ColumnResult(name = "email1", type=String.class),
                        @ColumnResult(name = "email2", type=String.class),
                        @ColumnResult(name = "mobile1", type=String.class),
                        @ColumnResult(name = "mobile2", type=String.class),
                        @ColumnResult(name = "designation", type=String.class),
                        @ColumnResult(name = "address", type=String.class),
                        @ColumnResult(name = "deal_id", type=Long.class),
                        @ColumnResult(name = "deal_name", type=String.class),
                }))
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Contact extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email1;

    private String email2;

    private String mobile1;

    private String mobile2;

    private String designation;

    private String address;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn
    private Deal deal;

}
