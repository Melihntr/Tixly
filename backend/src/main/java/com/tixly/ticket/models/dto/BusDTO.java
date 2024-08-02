package com.tixly.ticket.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {

    private Long id;
    private String plateNo;
    private Long companyId;
    private String busType;
    private int seatNo;

}
