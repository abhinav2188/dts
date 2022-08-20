package com.example.art.controllers.internal;

import com.example.art.dto.response.*;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.BrochureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/int/brochures")
public class BrochureIntController {

    @Autowired
    private BrochureService brochureService;

    @PostMapping
    public BaseResponse<BrochureResponse> addBrochure(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("brochureName") String brochureName)
            throws DocumentStorageException, NoAuthorizationException {
        return brochureService.addBrochure(file,brochureName);
    }

    @GetMapping(path = "/all")
    public BaseResponse<BrochuresResponse> getAllPartiesDetails(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "pageCount", defaultValue = "20", required = false) Integer pageCount ){
        return brochureService.getAllBrochures(pageNo, pageCount);
    }

}
