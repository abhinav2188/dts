package com.example.art.services.impl;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.mapper.DealMapper;
import com.example.art.dto.request.*;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.InvalidFieldException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Deal;
import com.example.art.model.Party;
import com.example.art.model.User;
import com.example.art.repository.DealRepository;
import com.example.art.repository.PartyRepository;
import com.example.art.repository.UserRepository;
import com.example.art.services.DealService;
import com.example.art.utils.Constants;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private StringUtils stringUtils;

    @Override
    public BaseResponse<CreateDealResponse> createDeal(CreateDealRequest requestDto)
            throws InvalidFieldException, DuplicateEntryException {

        validateCreateDealRequest(requestDto);

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                ()-> new InvalidFieldException("userId"));

        Party party = partyRepository.findById(requestDto.getPartyId()).orElseThrow(
                ()-> new InvalidFieldException("partyId"));

        Deal deal = dealMapper.getDeal(requestDto);

        user.addDeal(deal);
        party.addDeal(deal);

        Deal saved = dealRepository.save(deal);

        log.info("new deal created with name {}, with party {}, by user {}",
                deal.getName(), party.getPartyName(), user.getEmail());

        return BaseResponse.<CreateDealResponse>builder()
                .responseMsg("deal created")
                .status(HttpStatus.CREATED)
                .data(dealMapper.getCreateDealResponse(saved))
                .build();
    }

    @Override
    public BaseResponse updateProductDetails(Long dealId, UpdateProductDetailsRequest requestDto)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        int updateCount = dealMapper.updateProductDetails(deal,requestDto);

        dealRepository.save(deal);

        String msg = "Updated " + updateCount + " fields of deal with id=" + dealId;
        return BaseResponse.builder()
                .responseMsg(msg)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse updateDealSection3(Long dealId, UpdateDealSection3Request requestDto)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        int updateCount = dealMapper.updateDeal(deal,requestDto);

        dealRepository.save(deal);

        String msg = "Updated " + updateCount + " fields of deal with id=" + dealId;
        return BaseResponse.builder()
                .responseMsg(msg)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse updateDealSection4(Long dealId, UpdateDealSection4Request requestDto)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        int updateCount = dealMapper.updateDeal(deal,requestDto);

        dealRepository.save(deal);

        String msg = "Updated " + updateCount + " fields of deal with id=" + dealId;
        return BaseResponse.builder()
                .responseMsg(msg)
                .status(HttpStatus.CREATED)
                .build();

    }

    @Override
    public BaseResponse updateDealAuthorization(Long dealId, UpdateDealAuthorizationRequest request)
            throws EntityNotFoundException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        if(!request.getCoOwnerIds().contains(request.getOwnerId())){
            request.getCoOwnerIds().add(request.getOwnerId());
        }

        List<User> coOwners = deal.getCoOwners();

        List<Long> oldIds = coOwners.stream().map(User::getId).collect(Collectors.toList());

        List<Long> addedIds = request.getCoOwnerIds().stream()
                .filter(id -> !oldIds.contains(id)).collect(Collectors.toList());
        List<Long> removedIds = oldIds.stream()
                .filter(id -> !request.getCoOwnerIds().contains(id)).collect(Collectors.toList());

        List<String> emailsAdded = new ArrayList<>();
        List<String> emailsRemoved = new ArrayList<>();

        for(Long userId : addedIds){
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                user.addDeal(deal);
                emailsAdded.add(user.getEmail());
            }
        }
        for(User user : coOwners){
            if(removedIds.contains(user.getId())){
                deal.removeUser(user);
                emailsRemoved.add(user.getEmail());
            }
        }

        log.info("Updated co-owners for deal with id={}, added {}, removed {}", dealId, emailsAdded, emailsRemoved);

        String msg2 = "";
        // owner update
        if(request.getOwnerId() != null){
            User owner = deal.getOwner();
            if(owner == null || !request.getOwnerId().equals(owner.getId())){
                Optional<User> optionalUser = userRepository.findById(request.getOwnerId());
                if(optionalUser.isPresent()){
                    User user = optionalUser.get();
                    deal.setOwner(user);
                    log.info("Updated owner for deal with id={}, added {}, removed {}",dealId,
                            user.getEmail(), owner==null?"null":owner.getEmail());
                }
            }
        }

        dealRepository.save(deal);

        String msg = "Updated owners for deal with id=" + dealId +
                ", added " + addedIds.size() +
                ", removed " + removedIds.size() + " users ";

        return BaseResponse.builder()
                .responseMsg(msg)
                .status(HttpStatus.CREATED)
                .build();

        // todo : try to optimize query
    }

    private User validateUserAuthorization(Deal deal) throws NoAuthorizationException {
        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        return deal.getCoOwners().stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .orElseThrow(
                        () -> new NoAuthorizationException("user is not authorized to update this deal"));
    }

    private void validateCreateDealRequest(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException {
        if(dealRepository.existsByName(requestDto.getDealName())){
            throw new DuplicateEntryException("dealName");
        }
    }

}
