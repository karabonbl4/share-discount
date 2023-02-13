package com.senla.repository.impl;

import com.senla.config.ConnectionHolder;
import com.senla.model.DiscountCard;
import com.senla.model.User;
import com.senla.repository.CustomRepository;
import com.senla.repository.DefaultRepository;
import com.senla.repository.mapper.UserMapper;
import com.senla.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepository implements CustomRepository<User> {
    private static final String SELECT_BY_ID = "SELECT * FROM \"user\" u\n" +
            "JOIN discount_card dc ON u.id = dc.owner_user_id \n" +
            "JOIN discount_policy dp ON dc.discount_policy_id = dp.id \n" +
            "JOIN trademark t ON dp.trademark_id = t.id \n" +
            "WHERE u.id = ?";
    private static final String INSERT_INTO_USER = "insert into \"user\" (firstname, surname, phone_number, email, birthday, score, is_active, id) values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "update \"user\" set firstname = ?, surname = ?, phone_number = ?, " +
                                                "email = ?, birthday = ?, score = ?, is_active = ? where id=?";
    private static final String DELETE_USER = "delete from \"user\" where id = ?";
    private final ConnectionHolder connectionHolder;
    private final UserMapper userMapper;


    @SneakyThrows
    @Override
    public User findById(Long id) {
        Connection connection = connectionHolder.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        Set<DiscountCard> cards = new HashSet<>();
        while (resultSet.next()) {
            user = userMapper.convertToUserDto(resultSet);
            cards.add(user.getCards().get(0));
        }
        preparedStatement.close();
        List<DiscountCard> collect = new ArrayList<>(cards);
        if (!collect.isEmpty()){
            assert user != null;
            user.setCards(collect);
        }
        return user;
    }

    @SneakyThrows
    @Override
    public User saveOrUpdate(User user) {
        User checkUser = findById(user.getId());
        if ( checkUser != null) {
            Connection connection = connectionHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, Objects.requireNonNull(user.getFirstName()));
            preparedStatement.setString(2, Objects.requireNonNull(user.getSurName()));
            preparedStatement.setString(3, Objects.requireNonNull(user.getPhoneNumber()));
            preparedStatement.setString(4, Objects.requireNonNull(user.getEmail()));
            preparedStatement.setDate(5, Objects.requireNonNull(Date.valueOf(user.getBirthday())));
            preparedStatement.setBigDecimal(6, Objects.requireNonNull(user.getScore()));
            preparedStatement.setBoolean(7, Objects.requireNonNull(user.getIsActive()));
            preparedStatement.setLong(8, user.getId());
            if (preparedStatement.executeUpdate() != 0){
                preparedStatement.close();
                return user;
            }
            preparedStatement.close();
            return null;
        }
        Connection connection = connectionHolder.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USER);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getSurName());
        preparedStatement.setString(3, user.getPhoneNumber());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setDate(5, Date.valueOf(user.getBirthday()));
        preparedStatement.setBigDecimal(6, user.getScore());
        preparedStatement.setBoolean(7, user.getIsActive());
        preparedStatement.setLong(8, user.getId());
        if (preparedStatement.executeUpdate() != 0) {
            preparedStatement.close();
            return user;
        }
        preparedStatement.close();
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(User user) {
        Connection connection = connectionHolder.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
        preparedStatement.setLong(1, user.getId());
        preparedStatement.executeUpdate();

    }
}
