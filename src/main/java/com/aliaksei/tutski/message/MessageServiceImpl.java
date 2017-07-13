package com.aliaksei.tutski.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliaksei.tutski.exceptions.UserNotFoundException;
import com.aliaksei.tutski.user.User;
import com.aliaksei.tutski.user.UserService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepo;

    @Autowired
    UserService userService;

    @Override
    public Message createMessageForUser(Message message, String userName) throws UserNotFoundException {
        User user = userService.getUserOrCreateNew(userName);
        message.setUser(user);
        message.setCreatedTime(LocalDateTime.now());
        messageRepo.save(message);
        return message;
    }

    @Override
    public void addMessage(Message message) {
        messageRepo.save(message);
    }

    @Override
    public Message getMessage(Integer id) {
        return messageRepo.findOne(id);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<>();
        messageRepo.findAll().forEach(list::add);
        return list;
    }

    @Override
    public List<Message> getAllUserMessages(String userName) throws UserNotFoundException {
        User user = userService.getUser(userName);
        List<Message> list = new ArrayList<>();
        // TODO
        // make DTO for nice look
        messageRepo.findByUserOrderByCreatedTimeDesc(user).forEach(list::add);
        return list;
    }

    @Override
    public List<Message> getAllFollowingUserMessages(String userName) throws UserNotFoundException {
        User user = userService.getUser(userName);
        List<Message> list = new ArrayList<>();
        // TODO
        // make DTO for nice look
        messageRepo.findByUserInOrderByCreatedTimeDesc(userService.getFollowingUsersForUser(user)).forEach(list::add);
        return list;
    }
}
