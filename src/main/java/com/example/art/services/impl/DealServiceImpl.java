package com.example.art.services.impl;

import com.example.art.dto.mapper.DealMapper;
import com.example.art.dto.request.*;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealDetailResponse;
import com.example.art.dto.response.MultipleDealsResponse;
import com.example.art.dto.response.inner.DealCardDetails;
import com.example.art.dto.response.inner.DealUserDetails;
import com.example.art.exceptions.*;
import com.example.art.model.Deal;
import com.example.art.model.Party;
import com.example.art.model.User;
import com.example.art.model.enums.DealSubject;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.DealRepository;
import com.example.art.repository.PartyRepository;
import com.example.art.repository.UserRepository;
import com.example.art.services.DealHistoryService;
import com.example.art.services.DealService;
import com.example.art.services.helper.ServiceUtils;
import com.example.art.utils.Constants;
import com.example.art.utils.MessageUtils;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private DealHistoryService dealHistoryService;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public BaseResponse<DealCardDetails> createDeal(CreateDealRequest requestDto)
            throws InvalidFieldException, DuplicateEntryException {

        validateCreateDealRequest(requestDto);

        Long userId = Long.valueOf(MDC.get(Constants.USER_ID));
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new InvalidFieldException("userId"));

        Party party = partyRepository.findByPartyName(requestDto.getPartyName()).orElseThrow(
                ()-> new InvalidFieldException("partyName"));

        Deal deal = dealMapper.getDeal(requestDto);

        user.addDeal(deal);
        party.addDeal(deal);

        Deal saved = dealRepository.save(deal);

        log.info("new deal created with name {}, with party {}, by user {}",
                deal.getName(), party.getPartyName(), user.getEmail());

        DealCardDetails dealCardDetails = dealMapper.getDealCardDetails(saved);

        dealHistoryService.addDealHistory(saved.getId(), DealSubject.CREATED_DEAL, dealCardDetails);

        return BaseResponse.<DealCardDetails>builder()
                .responseMsg("deal created")
                .status(HttpStatus.CREATED)
                .data(dealCardDetails)
                .build();
    }

    @Override
    public BaseResponse updateProductDetails(Long dealId, UpdateProductDetailsRequest requestDto)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        Map<String,String> updates =  dealMapper.updateProductDetails(deal,requestDto);

        Deal deal1 = dealRepository.save(deal);

        dealHistoryService.addDealHistory(deal1.getId(), DealSubject.UPDATED_DEAL_PRODUCT_DETAILS, updates);

        //        String msg = "Updated " + updateCount + " fields of deal with id=" + dealId;
        return BaseResponse.builder()
                .responseMsg("Deal Updated")
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse updateDealSection3(Long dealId, UpdateDealSection3Request requestDto)
            throws EntityNotFoundException, NoAuthorizationException {


        requestDto.setExpectedNumberOfDays(calculateDays(requestDto.getOpeningDate(), requestDto.getExpectedCloseDate()));

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        Map<String,String> updates = dealMapper.updateDeal(deal,requestDto);
        log.info("section3 updates: {}",updates);

        Deal deal1 = dealRepository.save(deal);

        dealHistoryService.addDealHistory(deal1.getId(), DealSubject.UPDATED_DEAL_COMMON_DETAILS, updates);

        return BaseResponse.builder()
                .responseMsg("Deal Updated")
                .status(HttpStatus.CREATED)
                .build();
    }

    private Integer calculateDays(Date openingDate, Date closeDate) {
        if(openingDate==null || closeDate==null) return null;
        Long t1 = openingDate.getTime();
        Long t2 = closeDate.getTime();
        if(t1>=t2) return 0;
        return (int)((t2-t1)/(1000*60*60*24));
    }

    @Override
    public BaseResponse updateDealSection4(Long dealId, UpdateDealSection4Request requestDto)
            throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        User user = validateUserAuthorization(deal);

        Map<String,String> updates = dealMapper.updateDeal(deal,requestDto);

        Deal deal1 = dealRepository.save(deal);

        dealHistoryService.addDealHistory(deal1.getId(), DealSubject.UPDATED_DEAL_ADDITIONAL_DETAILS, updates);

        return BaseResponse.builder()
                .responseMsg("Deal Updated")
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

    @Override
    public BaseResponse<MultipleDealsResponse> getMultipleDeals(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updateTimestamp").descending());
        Page<Deal> page = null;
        if(isUserAdmin()){
            page = dealRepository.findAll(pageable);
        }else{
            Long userId = getCurrentUserId();
            page = dealRepository.findAllByCoOwners_Id(userId,pageable);
        }

        MultipleDealsResponse response = dealMapper.getMultipleDealsResponse(page);

        String msg = MessageUtils.successMultipleDealResponse(response.getDeals().size());

        return BaseResponse.<MultipleDealsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(msg)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<DealDetailResponse> getDealDetails(Long dealId, Long userId) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        if(!isUserAdmin()){
//            if(!getCurrentUserId().equals(userId))0
//                throw new NoAuthorizationException(MessageUtils.noAuthorization("Deal"));
            validateUserAuthorization(deal);
        }

        DealDetailResponse response = dealMapper.getDealDetailResponse(deal, deal.getOwner(), deal.getCoOwners());

        return BaseResponse.<DealDetailResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successGetMessage("Deal"))
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<DealDetailResponse> getDealDetails2(Long dealId, Long userId) throws EntityNotFoundException, NoAuthorizationException {

        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkDealOwnership(deal);

        DealDetailResponse response = dealMapper.getDealDetailResponse(deal, deal.getOwner(), deal.getCoOwners());

        return BaseResponse.<DealDetailResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successGetMessage("Deal"))
                .data(response)
                .build();

    }

    @Override
    public BaseResponse<DealUserDetails> addDealOwner(Long dealId, String userEmail) throws EntityNotFoundException, NoAuthorizationException, InvalidOperationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        if(!isUserAdmin()){
            validateUserAuthorization(deal);
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow( () -> new EntityNotFoundException("User","email",userEmail));

        if(userRepository.existsByCoOwnedDeals_IdAndEmail(dealId,userEmail)){
            throw new InvalidOperationException("user already a co-owner");
        }

        user.addDeal(deal);

        Deal deal1 = dealRepository.save(deal);

        dealHistoryService.addDealHistory(deal1.getId(), DealSubject.UPDATED_DEAL_AUTHORIZATION, "added "+userEmail +" as a deal owner");

        return BaseResponse.<DealUserDetails>builder()
                .status(HttpStatus.OK)
                .responseMsg("added "+userEmail +" as a deal owner")
                .data(dealMapper.getDealUserDetails(user))
                .build();

    }

    @Override
    public BaseResponse<DealUserDetails> removeDealOwner(Long dealId, String userEmail) throws NoAuthorizationException, EntityNotFoundException, InvalidOperationException {

        Deal deal = dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));

        if(!isUserAdmin()){
            validateUserAuthorization(deal);
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow( () -> new EntityNotFoundException("User","email",userEmail));

        if(!userRepository.existsByCoOwnedDeals_IdAndEmail(dealId,userEmail)){
            throw new InvalidOperationException("User not a co-owner to be removed");
        }
        if(getCurrentUserId().equals(user.getId())){
            throw new InvalidOperationException("User can't remove itself from deal owners list");
        }

        deal.removeUser(user);
        dealRepository.save(deal);

        dealHistoryService.addDealHistory(deal.getId(), DealSubject.UPDATED_DEAL_AUTHORIZATION, "removed "+userEmail +" from deal owners");

        return BaseResponse.<DealUserDetails>builder()
                .status(HttpStatus.OK)
                .responseMsg("removed "+userEmail +" from the deal owners")
                .data(dealMapper.getDealUserDetails(user))
                .build();

    }

    @Override
    public BaseResponse<MultipleDealsResponse> getMultipleDeals(int pageNo, int pageSize, String dealName, String partyName, String coOwnerEmail) {

        if(!stringUtils.isNotEmpty(dealName)) dealName="%";
        if(!stringUtils.isNotEmpty(partyName)) partyName="%";
        if(!stringUtils.isNotEmpty(coOwnerEmail)) coOwnerEmail="%";

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updateTimestamp").descending());
        Page<Deal> page = null;

        if(isUserAdmin()){
            page = dealRepository.findDistinctByCoOwners_EmailLikeAndParty_PartyNameLikeAndNameLike(coOwnerEmail,partyName,dealName,pageable);
        }else{
            Long userId = getCurrentUserId();
            page = dealRepository.findDistinctByCoOwners_IdAndCoOwners_EmailLikeAndParty_PartyNameLikeAndNameLike(
                    userId, coOwnerEmail, partyName, dealName, pageable);
        }

        MultipleDealsResponse response = dealMapper.getMultipleDealsResponse(page);

        String msg = MessageUtils.successMultipleDealResponse(response.getDeals().size());

        return BaseResponse.<MultipleDealsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(msg)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<MultipleDealsResponse> getAllUserDeals(int pageNo, int pageSize, String dealName, String partyName, String coOwnerEmail) {

        if(!stringUtils.isNotEmpty(dealName)) dealName="%";
        if(!stringUtils.isNotEmpty(partyName)) partyName="%";
        if(!stringUtils.isNotEmpty(coOwnerEmail)) coOwnerEmail="%";

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updateTimestamp").descending());

        Long userId = getCurrentUserId();
        Page<Deal> page = dealRepository.findDistinctByCoOwners_IdAndCoOwners_EmailLikeAndParty_PartyNameLikeAndNameLike(
                userId, coOwnerEmail, partyName, dealName, pageable);

        MultipleDealsResponse response = dealMapper.getMultipleDealsResponse(page);

        String msg = MessageUtils.successMultipleDealResponse(response.getDeals().size());

        return BaseResponse.<MultipleDealsResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg(msg)
                .data(response)
                .build();
    }

    private boolean isUserAdmin() {
        String roles = MDC.get(Constants.USER_ROLES);
        if(roles != null)
            return roles.contains(UserRole.ADMIN.name());
        return false;
    }

    private Long getCurrentUserId(){
        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        return userId;
    }

    private User validateUserAuthorization(Deal deal) throws NoAuthorizationException, EntityNotFoundException {
        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        if(isUserAdmin()) return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User","userId",userId));
        return deal.getCoOwners().stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .orElseThrow(
                        () -> new NoAuthorizationException(MessageUtils.noAuthorization("Deal")));
    }

    private void validateCreateDealRequest(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException {
        if(dealRepository.existsByName(requestDto.getDealName())){
            throw new DuplicateEntryException("dealName");
        }
    }

}
