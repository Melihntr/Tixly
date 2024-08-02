package com.tixly.ticket.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;
    private String name;
    private String companyNo;
    private String website;
    private String contactPhone;
    private String contactEmail;
    private String logoUrl;

}