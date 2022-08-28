package com.example.art.repository;

import com.example.art.dto.response.inner.DropdownValueDetails;
import com.example.art.model.DropdownValue;
import com.example.art.model.enums.DropdownType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface DropdownRepository extends JpaRepository<DropdownValue,Long> {

    List<DropdownValueDetails> findByDropdownTypeOrderByValueOrderAsc(DropdownType dropdownType);

    List<DropdownValueDetails> findAllByDropdownType(DropdownType dropdownType);

    List<DropdownValue> findByDropdownTypeIn(List<DropdownType> dropdownTypes);

    List<DropdownValue> findByDropdownTypeInOrderByValueOrderAsc(Collection<DropdownType> dropdownTypes);

    List<DropdownValue> findByOrderByValueOrderAsc();

}
