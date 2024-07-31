package com.tixly.ticket.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tixly.ticket.models.dto.TicketModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private Long customerId;
    private Long tripId;
    private String from; // New field
    private String to;   // New field
    private Long seatId; // New field
    private Date printDate;
    private Date checkoutDate;
    private Date purchaseDate;
    private UUID invoiceId;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ticket(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TicketModel> getTicketsByCustomerId(Long customerId) {
        String sql = "SELECT * FROM tickets WHERE customerid = ?";
        return jdbcTemplate.query(sql, new Object[]{customerId}, (rs, rowNum) -> new TicketModel(
            rs.getLong("id"),
            rs.getLong("customerid"),
            rs.getLong("tripid"),
            rs.getString("from"), 
            rs.getString("to"),    
            rs.getLong("seatid"),  
            rs.getDate("printdate"),
            rs.getDate("checkoutdate"),
            rs.getDate("purchasedate"),
            (UUID) rs.getObject("invoiceid")
        ));
    }
    public List<Long> getTicketIdsByCustomerId(Long customerId) {
        String sql = "SELECT id FROM tickets WHERE customerid = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{customerId}, Long.class);
    }
    public void addTicket(TicketModel ticketModel) {
        String sql = "INSERT INTO tickets (customerid, tripid, \"from\", \"to\", seatid, printdate, checkoutdate, purchasedate, invoiceid) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                ticketModel.getCustomerId(),
                ticketModel.getTripId(),
                ticketModel.getFrom(),
                ticketModel.getTo(),
                ticketModel.getSeatId(),
                ticketModel.getPrintDate(),
                ticketModel.getCheckoutDate(),
                ticketModel.getPurchaseDate(),
                ticketModel.getInvoiceId()
        );
    }
}
