package com.example.uploaddownloadfile.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.uploaddownloadfile.entity.master.Customer}
 */

@Data
public class CustomerDto implements Serializable {
    private String customerName;
    private String contactLastName;
    private Integer customerNumber;
    private String contactFirstName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Integer salesRepEmployeeNumber;
    private Long creditLimit;
}