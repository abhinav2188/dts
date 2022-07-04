package com.example.art.repository;

import com.example.art.model.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartyRepository extends CrudRepository<Party, Long> {

    Optional<Party> findByPartyName(String partyName);

}
