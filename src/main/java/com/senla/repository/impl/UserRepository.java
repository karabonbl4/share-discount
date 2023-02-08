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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepository implements CustomRepository<User> {
    private static final String SELECT_BY_ID = "SELECT * FROM \"user\" u\n" +
            "JOIN discount_card dc ON u.id = dc.owner_user_id \n" +
            "JOIN discount_policy dp ON dc.discount_policy_id = dp.id \n" +
            "JOIN trademark t ON dp.trademark_id = t.id \n" +
            "WHERE u.id = ?";
    private static final String INSERT_INTO_USER = "insert into \"user\" (firstname, surname, email) values(?, ?, ?)";
    private static final String UPDATE_USER = "update \"user\" set title = ? where id=?";
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
        while (resultSet.next()){
            user = userMapper.convertToUserDto(resultSet);
            cards.add(user.getCards().get(0));
        }
        preparedStatement.close();
        List<DiscountCard> collect = cards.stream().collect(Collectors.toList());
        assert user != null;
        user.setCards(collect);
        return user;
    }

    @Override
    public User saveOrUpdate(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
