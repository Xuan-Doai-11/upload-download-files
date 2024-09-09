package com.example.uploaddownloadfile.controller;

import com.example.uploaddownloadfile.dto.ApiMessage;
import com.example.uploaddownloadfile.util.HashHelper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ApiController {

    @Autowired
    private Gson gson;

    public ApiController() {
        if (gson == null) {
            gson = new Gson();
        }
    }

    public String encryptResponse(Object data) {
        return HashHelper.encryptBase64(gson.toJson(data));
    }

    protected ResponseEntity<String> ok(Object data){
        ApiMessage apiMessage = ApiMessage
                .builder()
                .success(true)
                .code("200")
                .message("OK")
                .data(gson.toJson(data))
                .build();
        return new ResponseEntity<>(encryptResponse(apiMessage), HttpStatus.OK);
    }




}
