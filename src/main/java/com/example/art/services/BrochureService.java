package com.example.art.services;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.BrochureResponse;
import com.example.art.dto.response.BrochuresResponse;
import com.example.art.dto.response.SuccessCreateResponse;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BrochureService {

    BaseResponse<BrochureResponse> addBrochure(MultipartFile file, String brochureName) throws DocumentStorageException, NoAuthorizationException;

    BaseResponse<BrochuresResponse> getAllBrochures(Integer pageNo, Integer pageCount);
}
