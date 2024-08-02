package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompanyRowMapper implements RowMapper<CompanyDTO> {
    @Override
    public CompanyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompanyDTO company = new CompanyDTO();
        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        company.setCompanyNo(rs.getString("companyNo"));
        company.setWebsite(rs.getString("website"));
        company.setContactPhone(rs.getString("contact_phone"));
        company.setContactEmail(rs.getString("contact_email"));
        company.setLogoUrl(rs.getString("logo_url"));
        return company;
    }
}