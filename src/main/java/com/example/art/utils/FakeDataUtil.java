package com.example.art.utils;

import com.example.art.model.*;
import com.example.art.model.enums.UserRole;
import com.github.javafaker.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class FakeDataUtil {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Faker faker;
    private Random random;

    List<String> fakeProductTypes;
    List<String> fakeSubProductTypes;
    List<String> fakeTypeOfWorks;
    List<String> fakeUnitOfQuantity;
    List<String> fakeVerticals;

    @PostConstruct
    private void postConstruct(){
        faker = new Faker(new Locale("en-IND"));
        random = new Random();
        fakeProductTypes = new ArrayList<>();
        fakeSubProductTypes = new ArrayList<>();
        fakeTypeOfWorks = new ArrayList<>();
        fakeUnitOfQuantity = new ArrayList<>();
        fakeVerticals = new ArrayList<>();
        populateDropdownFields();
    }

    private void populateDropdownFields(){
        for(int i=0;i<10;i++){
            fakeProductTypes.add(faker.commerce().department());
        }
        for(int i=0;i<15;i++){
            fakeSubProductTypes.add(faker.commerce().productName());
            fakeUnitOfQuantity.add(faker.currency().name());
        }
        for(int i=0;i<7;i++){
            fakeTypeOfWorks.add(faker.slackEmoji().activity());
            fakeVerticals.add(faker.address().cityName());
        }
    }

    public User getFakeUser(){
        User user = new User();
        Pokemon pokemon = faker.pokemon();
        PhoneNumber phoneNumber = faker.phoneNumber();
        user.setEmail(pokemon.name()+"@"+faker.company().name()+".com");
        user.setMobile(phoneNumber.phoneNumber());
        user.setPassword(passwordEncoder.encode(phoneNumber.phoneNumber()));
        user.setRoles(UserRole.values()[random.nextInt(3)].name());
        user.setIsActive(true);
        user.setCoOwnedDeals(new ArrayList<>());
        return user;
    }

    public Party getFakeParty(){
        Party party = new Party();
        Company company = faker.company();
        Address address = faker.address();
        party.setPartyName(company.name());
        party.setAddress(faker.address().fullAddress());
        party.setIsActive(true);
        party.setAuthority(company.industry());
        party.setMobile(faker.phoneNumber().phoneNumber());
        party.setEmail("contact@"+company.name()+".org");
        party.setDeals(new ArrayList<>());
        return party;
    }

    // generates all primitive type data.. no mappings related fields are set here
    public Deal getFakeDeal(Party party, User owner){
        Deal deal = new Deal();
        // section 1
        deal.setName("D_"+faker.lorem().characters(2,20));
        // section 2
        deal.setProductType(fakeProductTypes.get(random.nextInt(10)));
        deal.setSubCategoryProduct(fakeSubProductTypes.get(random.nextInt(15)));
        deal.setUnitOfQuantity(fakeUnitOfQuantity.get(random.nextInt(15)));
        deal.setOrderSizeFactor(String.valueOf(random.nextInt(10)));
        deal.setTypeOfWork(fakeTypeOfWorks.get(random.nextInt(7)));
        deal.setRoadDetails(faker.lorem().characters(random.nextInt(100)));
        // section 3
        deal.setSiteLocation(faker.address().fullAddress());
        deal.setCateredByVertical(fakeVerticals.get(random.nextInt(7)));
        deal.setOpeningDate(faker.date().past(30, TimeUnit.DAYS));
        deal.setExpectedCloseDate(faker.date().future(100, TimeUnit.DAYS));
        deal.setCompetitorsInfo(faker.lorem().characters(10,50));
        deal.setRemarks(faker.lorem().characters(20,100));
        // section 4
        deal.setIsActive(true);
        deal.setDealValueInCr(faker.number().randomDouble(2,1,1000));
        deal.setPaymentFactor(faker.number().randomDouble(2,0,100));
        deal.setOwnerFocus(faker.number().numberBetween(1,10));
        deal.setDealProbability(faker.number().randomDouble(2,0,1));
        // section 5
        deal.setCoOwners(new ArrayList<>());
        return deal;
    }

}