package com.tixly.ticket.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tixly.ticket.models.dto.ProvinceModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province {

  
    
    @Id
    private Long id;
    private String name;

    private JdbcTemplate jdbcTemplate;

   
    public Province(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Setter for JdbcTemplate
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ProvinceModel> getAllProvinces() {
        String sql = "SELECT id,il_adi FROM iller";
        return jdbcTemplate.query(sql, new ProvinceModel.ProvinceRowMapper());

    }
    public List<ProvinceModel> getwedfvewProvinces() {
        String sql = "SELECT id,il_adi FROM iller";
        return jdbcTemplate.query(sql, new ProvinceModel.ProvinceRowMapper());

    }
}

