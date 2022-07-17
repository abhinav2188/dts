package com.example.art.controllers.internal;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.UpdateUserAuthorizationRequest;
import com.example.art.dto.request.UpdateUserProfileRequest;
import com.example.art.exceptions.UserNotFoundException;
import com.example.art.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/int/users")
public class UserIntController {

    @Autowired
    private UserService userService;

    @GetMapping
    public BaseResponse getAllUsers(@RequestParam(name="pageNo") int pageNo,
                                    @RequestParam(name="pageSize", defaultValue="10", required=false) int pageSize){
        return userService.getAllUsers(pageNo, pageSize);
    }

    @PatchMapping("/auth/{id}")
    public BaseResponse updateUserAuthorization(@PathVariable Long id,
                                                @RequestBody UpdateUserAuthorizationRequest requestBody)
            throws UserNotFoundException, IllegalAccessException, NoSuchFieldException {
        return userService.updateUserAuthorization(id, requestBody);
    }

    @PatchMapping("/{id}")
    public BaseResponse updateUserProfileDetails(@PathVariable Long id,
                                                 @RequestBody UpdateUserProfileRequest requestBody) throws UserNotFoundException {
        return userService.updateUserProfile(id, requestBody);
    }

}
