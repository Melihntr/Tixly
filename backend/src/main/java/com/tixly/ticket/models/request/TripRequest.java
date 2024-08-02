package com.tixly.ticket.models.request;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TripRequest {
    private Long id;
    private String peronNo;
    private String departureLocationId;
    private String arrivalLocationId;
    private int estimatedTime;
    private Double price;
    private Long busId;
    private LocalDateTime departureTime;
}