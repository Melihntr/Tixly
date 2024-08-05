package com.tixly.ticket.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
    private Long id;
    private Long ticketId;
    private int seatId;
    private Long tripId;
    private String gender;
}

