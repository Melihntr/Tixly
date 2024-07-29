package com.tixly.ticket.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusRequest {
    private String plateNo;
    private int seatNo;
    private Long companyId;
    private String busType;
}