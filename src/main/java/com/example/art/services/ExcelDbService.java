package com.example.art.services;

import com.example.art.exceptions.NoAuthorizationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelDbService {

    void getDbExcel(HttpServletResponse response) throws IOException, NoAuthorizationException;

}
