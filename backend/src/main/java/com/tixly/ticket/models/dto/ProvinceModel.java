package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProvinceModel{
    public final long id;
    public final String il_adi;

    public ProvinceModel(long _id, String _il_adi){
        this.id = _id;
        this.il_adi = _il_adi;
    }

    public static class ProvinceRowMapper implements RowMapper<ProvinceModel> {
        @Override
        public ProvinceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProvinceModel(
                rs.getLong("id"),
                rs.getString("il_adi")
            );
        }
    }   
};