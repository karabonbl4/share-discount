package com.senla.annotation;

import com.senla.config.ConnectionHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionHandler {

    private final ConnectionHolder connectionHolder;

    @SneakyThrows
    @Around("@annotation(Transaction)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Connection connection = connectionHolder.getConnection();
        connectionHolder.putToOpenedWithTransaction(connection);
        connection.setAutoCommit(false);
        Object object;
        try {
            object = joinPoint.proceed();
            connection.commit();
            return object;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }
}
