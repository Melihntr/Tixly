package com.tixly.ticket.models.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TripDTO {
    private Long id;
    private String peronNo;
    private String departureLocationId;
    private String arrivalLocationId;
    private int estimatedTime;
    private Double price;
    private Long companyId;
    private Long busId;
    private LocalDateTime departureTime;
    private String state;

    // Getters and setters
}

