package com.tixly.ticket.models.request;

import lombok.Data;

@Data
public class TripRequest {
    private Long id;
    private String peronNo;
    private int departureLocationId;
    private int arrivalLocationId;
    private int estimatedTime;
    private Double price;
    private int companyId;
    private Long busId;
}