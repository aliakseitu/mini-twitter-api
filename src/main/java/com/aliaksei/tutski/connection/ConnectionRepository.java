package com.aliaksei.tutski.connection;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.aliaksei.tutski.user.User;

public interface ConnectionRepository extends CrudRepository<Connection, Integer> {
    List<Connection> findByUser(User user);
}
