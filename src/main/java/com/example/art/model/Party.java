package com.example.art.model;

import com.example.art.dto.request.AddPartyRequestDto;
import com.example.art.model.abstracts.Timestamps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Party extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partyName;

    private String address;

    private String authority;

    private boolean isActive;

    @OneToMany(mappedBy = "party", cascade = CascadeType.DETACH)
    private List<Deal> dealList;

    public Party(AddPartyRequestDto requestDto){
        this.partyName = requestDto.getPartyName();
        this.authority = requestDto.getAuthority();
        this.address = requestDto.getAddress();
        this.isActive = true;
    }

}
