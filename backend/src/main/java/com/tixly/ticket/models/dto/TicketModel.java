package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    private Long seatId;  // New field
    private Date printDate;
    private Date checkoutDate;
    private Date purchaseDate;
    private UUID invoiceId;

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong("id"))
                .customerId(rs.getLong("customerid"))
                .tripId(rs.getLong("tripid"))
                .from(rs.getString("from"))  // Map new field
                .to(rs.getString("to"))      // Map new field
                .seatId(rs.getLong("seatid")) // Map new field
                .printDate(rs.getDate("printdate"))
                .checkoutDate(rs.getDate("checkoutdate"))
                .purchaseDate(rs.getDate("purchasedate"))
                .invoiceId((UUID) rs.getObject("invoiceid"))
                .build();
    }
}