package com.tixly.ticket.models.request;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TripRequest {
    private Long id;
    private String peronNo;
    private int departureLocationId;
    private int arrivalLocationId;
    private int estimatedTime;
    private Double price;
    private Long companyId;
    private Long busId;
    private LocalDateTime departureTime;
}