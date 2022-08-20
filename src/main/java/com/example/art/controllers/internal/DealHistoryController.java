package com.example.art.controllers.internal;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealHistoryResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.DealHistoryService;
import com.example.art.services.helper.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealHistoryController {

    @Autowired
    private DealHistoryService dealHistoryService;

    @Autowired
    private ServiceUtils serviceUtils;

    @GetMapping("/api/int/{dealId}/history")
    public BaseResponse<DealHistoryResponse> getDealHistory(@PathVariable("dealId") Long dealId,
                                                            @RequestParam("pageNo") int pageNo,
                                                            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                                            int pageSize)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealHistoryService.getDealHistory(dealId, pageNo, pageSize);
    }

}
