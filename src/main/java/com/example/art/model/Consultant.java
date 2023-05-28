package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.views.ConsultantExcelView;
import com.example.art.model.views.ContactExcelView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@NamedNativeQuery(name = "Consultant.findAllConsultantExcelViews_Named",
        query = "select c.id, c.create_timestamp, c.update_timestamp, c.name, " +
                "c.email, c.mobile, c.designation, c.deal_id, d.name as deal_name " +
                "from consultant c join deal d on c.deal_id = d.id order by c.name",
        resultSetMapping = "Mapping.ConsultantExcelView")
@SqlResultSetMapping(name = "Mapping.ConsultantExcelView",
        classes = @ConstructorResult(targetClass = ConsultantExcelView.class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "create_timestamp", type= Date.class),
                        @ColumnResult(name = "update_timestamp", type=Date.class),
                        @ColumnResult(name = "name", type=String.class),
                        @ColumnResult(name = "email", type=String.class),
                        @ColumnResult(name = "mobile", type=String.class),
                        @ColumnResult(name = "designation", type=String.class),
                        @ColumnResult(name = "deal_id", type=Long.class),
                        @ColumnResult(name = "deal_name", type=String.class),
                }))
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Consultant extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String designation;

    @ManyToOne
    @JoinColumn
    private Deal deal;

}
