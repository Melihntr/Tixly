package com.tixly.ticket.models.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusRowMapper implements RowMapper<BusDTO> {

    @Override
    public BusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BusDTO bus = new BusDTO();
        bus.setId(rs.getLong("id"));
        bus.setPlateNo(rs.getString("plateno"));
        bus.setCompanyId(rs.getLong("companyid"));
        bus.setBusType(rs.getString("bustype"));
        bus.setSeatNo(rs.getInt("seatno"));
        return bus;
    }
}
