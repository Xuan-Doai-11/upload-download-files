package com.example.uploaddownloadfile.impl;

import com.example.uploaddownloadfile.dto.BaseResponse;
import com.example.uploaddownloadfile.dto.CustomerDto;
import com.example.uploaddownloadfile.dto.ImportConfig;
import com.example.uploaddownloadfile.entity.master.Customer;
import com.example.uploaddownloadfile.repository.CustomerRepository;
import com.example.uploaddownloadfile.service.CustomerService;
import com.example.uploaddownloadfile.util.ExcelUtil;
import com.example.uploaddownloadfile.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public BaseResponse importCustomerData(MultipartFile importFile) {
        BaseResponse baseResponse = new BaseResponse();

        Workbook workbook = FileUtil.getWorkbookStream(importFile);

        List<CustomerDto> customerDTOList = ExcelUtil.getImportData(workbook, ImportConfig.customerImport);

        if(!CollectionUtils.isEmpty(customerDTOList)){
            saveData(customerDTOList);
            baseResponse.setCode(String.valueOf(HttpStatus.OK));
            baseResponse.setMessage("Import successfully");
        }else{
            baseResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseResponse.setMessage("Import failed");
        }

        return baseResponse;
    }


    private void saveData(List<CustomerDto> customerDTOList){
        for(CustomerDto customerDTO : customerDTOList){
            Customer customer = new Customer();
            customer.setCustomerNumber(customerDTO.getCustomerNumber());
            customer.setCustomerName(customerDTO.getCustomerName());
            customer.setContactFirstName(customerDTO.getContactFirstName());
            customer.setContactLastName(customerDTO.getContactLastName());
            customer.setPhone(customerDTO.getPhone());
            customer.setAddressLine1(customerDTO.getAddressLine1());
            customer.setAddressLine2(customerDTO.getAddressLine2());
            customer.setCity(customerDTO.getCity());
            customer.setState(customerDTO.getState());
            customer.setPostalCode(customerDTO.getPostalCode());
            customer.setCountry(customerDTO.getCountry());
            customer.setSalesRepEmployeeNumber(customerDTO.getSalesRepEmployeeNumber());
            customer.setCreditLimit(customerDTO.getCreditLimit());
            customerRepository.save(customer);
        }
    }
}
