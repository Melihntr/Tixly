package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.tixly.ticket.entity.Ticket;
import java.util.Date;
import java.util.UUID;

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
                .printDate(rs.getDate("printdate"))
                .checkoutDate(rs.getDate("checkoutdate"))
                .purchaseDate(rs.getDate("purchasedate"))
                .invoiceId((UUID) rs.getObject("invoiceid"))
                .build();
    }
}