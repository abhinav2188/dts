package com.example.art.model;

import com.example.art.model.abstracts.Timestamps;
import com.example.art.model.views.InteractionExcelView;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@NamedNativeQuery(name = "Interaction.findAllInteractionExcelViews_Named",
        query = "select i.*, d.name as deal_name from interaction i join deal d on i.deal_id = d.id",
        resultSetMapping = "Mapping.InteractionExcelView")
@SqlResultSetMapping(name = "Mapping.InteractionExcelView",
        classes = @ConstructorResult(targetClass = InteractionExcelView.class,
                columns = {
                        @ColumnResult(name = "id", type=Long.class),
                        @ColumnResult(name = "create_timestamp", type= Date.class),
                        @ColumnResult(name = "update_timestamp", type=Date.class),
                        @ColumnResult(name = "meeting_date", type=Date.class),
                        @ColumnResult(name = "meeting_location", type=String.class),
                        @ColumnResult(name = "contacts", type=String.class),
                        @ColumnResult(name = "consultants", type=String.class),
                        @ColumnResult(name = "handlers", type=String.class),
                        @ColumnResult(name = "meeting_details", type=String.class),
                        @ColumnResult(name = "deal_id", type=Long.class),
                        @ColumnResult(name = "deal_name", type=String.class),
                        @ColumnResult(name = "deal_stage", type=String.class),
                }))
@Entity
@Data
public class Interaction extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date meetingDate;

    private String dealStage;

    private String meetingLocation;

    private String meetingDetails;

    private String contacts;

    private String interactions;

    private String handlers;

    // bidirectional
    @ManyToOne
    @JoinColumn
    private Deal deal;

}
