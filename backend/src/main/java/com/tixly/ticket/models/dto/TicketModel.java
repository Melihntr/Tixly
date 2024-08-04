package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.tixly.ticket.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketModel implements RowMapper<Ticket> {
    private Long id;
    private Long customerId;
    private Long tripId;
    private String from;  // New field
    private String to;    // New field
    private int  seatId;  // New field
    private LocalDateTime printDate;
    private LocalDateTime checkoutDate;
    private LocalDateTime purchaseDate;
    private UUID invoiceId;

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong("id"))
                .customerId(rs.getLong("customerid"))
                .tripId(rs.getLong("tripid"))
                .from(rs.getString("from"))  
                .to(rs.getString("to"))      
                .seatId(rs.getInt("seatid")) 
                .printDate(rs.getTimestamp("printdate").toLocalDateTime()) 
                .checkoutDate(rs.getTimestamp("checkoutdate").toLocalDateTime()) 
                .purchaseDate(rs.getTimestamp("purchasedate").toLocalDateTime())
                .invoiceId((UUID) rs.getObject("invoiceid"))
                .build();
    }
}