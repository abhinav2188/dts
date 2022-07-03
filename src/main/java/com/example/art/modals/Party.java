package com.example.art.modals;

import com.example.art.dto.request.AddPartyRequestDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Party extends Timestamps{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partyName;

    private String address;

    private String authority;

    private boolean isActive;

    public Party(AddPartyRequestDto requestDto){
        this.partyName = requestDto.getPartyName();
        this.authority = requestDto.getAuthority();
        this.address = requestDto.getAddress();
        this.isActive = true;
    }

}
