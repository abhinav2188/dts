package com.example.art.services;

import com.example.art.model.Deal;
import com.example.art.model.Party;
import com.example.art.model.User;
import com.example.art.repository.DealRepository;
import com.example.art.repository.PartyRepository;
import com.example.art.repository.UserRepository;
import com.example.art.utils.FakeDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class FakeDataService {

    @Autowired
    private FakeDataUtil fakeDataUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private DealRepository dealRepository;

    private Random random = new Random();

    public void createFakeData(){
        try{
            User user = fakeDataUtil.getFakeUser();
            user = userRepository.save(user);
            Party party = fakeDataUtil.getFakeParty();
            party = partyRepository.save(party);
            Deal deal = fakeDataUtil.getFakeDeal(party, user);
            user.addDeal(deal);
            party.addDeal(deal);
            dealRepository.save(deal);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFakeData(int usersCount, int partyCount, int dealsCount){

        List<User> userList = new ArrayList<>();
        List<Party> partyList = new ArrayList<>();

        for(int i=0;i<usersCount;i++){
            User user = fakeDataUtil.getFakeUser();
            try{
                User savedUser = userRepository.save(user);
                userList.add(savedUser);
            }catch (Exception e){
                // will catch any sql constraints exceptions
                // do nothing
            }
        }
        for(int i=0;i<partyCount; i++){
            Party party = fakeDataUtil.getFakeParty();
            try{
                party = partyRepository.save(party);
                partyList.add(party);
            }catch (Exception e){
                // do nothing
            }
        }
        for(int i=0;i<dealsCount;i++){
            try{
                User user = userList.get(random.nextInt(userList.size()));
                Party party = partyList.get(random.nextInt(partyList.size()));
                Deal deal = fakeDataUtil.getFakeDeal(party, user);
                user.addDeal(deal);
                party.addDeal(deal);
                dealRepository.save(deal);
            }catch (Exception e){
                log.error("error while saving fake deal, {}",e.getLocalizedMessage());
                // do nothing
            }
        }

    }

}
