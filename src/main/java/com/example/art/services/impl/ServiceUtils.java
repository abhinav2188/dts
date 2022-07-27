package com.example.art.services.impl;

import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Deal;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.DealRepository;
import com.example.art.utils.Constants;
import com.example.art.utils.MessageUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {

    @Autowired
    private DealRepository dealRepository;

    public Deal getDeal(Long dealId) throws EntityNotFoundException {
        return dealRepository.findById(dealId).orElseThrow(
                () -> new EntityNotFoundException("Deal","id",dealId));
    }

    public void checkUserAuthorization(Deal deal) throws NoAuthorizationException {
        if(isUserAdmin()) return;
        Long userId = getCurrentUserId();
        deal.getCoOwners().stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .orElseThrow(
                        () -> new NoAuthorizationException(MessageUtils.noAuthorization("Deal")));
    }

    public boolean isUserAdmin() {
        String roles = MDC.get(Constants.USER_ROLES);
        if(roles != null)
            return roles.contains(UserRole.ADMIN.name());
        return false;
    }

    public Long getCurrentUserId(){
        Long userId = Long.parseLong(MDC.get(Constants.USER_ID));
        return userId;
    }


}
