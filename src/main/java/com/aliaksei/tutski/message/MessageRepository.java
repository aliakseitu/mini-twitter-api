package com.aliaksei.tutski.message;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.aliaksei.tutski.user.User;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findByUserOrderByCreatedTimeDesc(User user);

    List<Message> findByUserInOrderByCreatedTimeDesc(Set<User> users);
}
