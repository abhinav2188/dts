package com.example.art.services.impl;

import com.example.art.dto.mapper.BrochureMapper;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.BrochureResponse;
import com.example.art.dto.response.BrochuresResponse;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Brochure;
import com.example.art.repository.BrochureRepository;
import com.example.art.services.BrochureService;
import com.example.art.services.FileStorageService;
import com.example.art.utils.MessageUtils;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class BrochureServiceImpl implements BrochureService {

    @Autowired
    private BrochureRepository brochureRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private BrochureMapper brochureMapper;

    @Autowired
    private FileStorageService fileStorageService;

    private final String UPLOAD_PATH = "brochures/";

    @Override
    public BaseResponse<BrochureResponse> addBrochure(MultipartFile file, String brochureName)
            throws DocumentStorageException, NoAuthorizationException {

        if(!serviceUtils.isUserAdmin()) throw new NoAuthorizationException("Brochure");

        try{
//            // generate custom file name
            String fname = getBrochureName(file.getOriginalFilename(), brochureName);
//
//            // create or replace file
//            Files.copy(file.getInputStream(), getBrochuresPath(fname), StandardCopyOption.REPLACE_EXISTING);
//
//            // upload path
//            String uploadPath = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path(UPLOAD_PATH).path(fname)
//                    .toUriString();

            String fileCode = fileStorageService.saveFile(fname,file);
            String downloadUri = "api/download/"+fileCode;

            // updating database brochure table
            Brochure saved = updateBrochureEntity(brochureName, downloadUri);

            // return response
            return BaseResponse.<BrochureResponse>builder()
                    .status(HttpStatus.OK)
                    .responseMsg(MessageUtils.successPostMessage("Brochure"))
                    .data(brochureMapper.getBrochureResponse(saved))
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error storing brochure in server fs");
            throw new DocumentStorageException();
        }
    }

    @Override
    public BaseResponse<BrochuresResponse> getAllBrochures(Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("brochureName").descending());
        Page<Brochure> brochurePage = brochureRepository.findAll(pageable);

        BrochuresResponse response = brochureMapper.getBrochuresResponse(brochurePage);

        return BaseResponse.<BrochuresResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .responseMsg(MessageUtils.successGetMessage("Brochures",response.getBrochures().size()))
                .build();
    }

    private Brochure updateBrochureEntity(String brochureName, String uploadPath) {
        Brochure brochure = brochureRepository.findByBrochureName(brochureName);
        if(brochure==null){
            brochure = new Brochure();
            brochure.setBrochureName(brochureName);
            brochure.setIsActive(true);
            brochure.setFilePath(uploadPath);
        }
        Brochure saved = brochureRepository.save(brochure);
        return saved;
    }

    private Path getBrochuresPath(String brochureName) throws IOException {
        String path = new ClassPathResource(UPLOAD_PATH).getFile().getAbsolutePath() +
                File.separator + brochureName;
        log.info("file path = {}",path);
        return Paths.get(path);
    }

    private String getBrochureName(String fileName, String brochureName){
        String splits[] = fileName.split("\\.");
        return splits.length == 0 ? brochureName :
                brochureName + "." + splits[splits.length-1];
    }

}
