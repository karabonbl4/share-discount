package com.senla.repository.mapper;

import com.senla.model.Trademark;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TrademarkMapper {

    public Trademark convertToTrademark(ResultSet resultSet){
        Trademark trademark = new Trademark();

        try {
            trademark.setId(resultSet.getLong(1));
            trademark.setTitle(resultSet.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trademark;
    }
}
