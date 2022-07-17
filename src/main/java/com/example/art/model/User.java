package com.example.art.model;

import com.example.art.dto.request.CreateUserRequest;
import com.example.art.model.abstracts.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends Timestamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    private String designation;

    @Column(nullable = false)
    private String password;

    private Boolean isActive;

    private String roles;

    @ManyToMany
    private List<Deal> dealList;

    @OneToMany(mappedBy = "handler")
    private List<Interaction> interactionList;

    @OneToMany(mappedBy = "owner")
    private List<Deal> ownedDealList;

    public User(CreateUserRequest createUserRequest){
        this.email = createUserRequest.getEmail();
        this.password = createUserRequest.getPassword();
        this.mobile = createUserRequest.getMobile();
        this.isActive = true;
    }

}
