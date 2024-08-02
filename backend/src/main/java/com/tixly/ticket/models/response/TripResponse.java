package com.tixly.ticket.models.response;

import java.util.List;

import com.tixly.ticket.models.dto.TripModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {

    private List<TripModel> trips;
    private String message;
    private int totalTrips;

    // You can add other fields as needed, such as pagination information
}
