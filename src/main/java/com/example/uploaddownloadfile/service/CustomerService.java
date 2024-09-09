package com.example.uploaddownloadfile.service;

import com.example.uploaddownloadfile.dto.BaseResponse;
import com.example.uploaddownloadfile.dto.CustomerDto;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    BaseResponse importCustomerData(MultipartFile importFile);


}
