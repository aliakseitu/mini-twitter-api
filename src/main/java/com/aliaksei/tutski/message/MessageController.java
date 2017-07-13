package com.aliaksei.tutski.message;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aliaksei.tutski.exceptions.ErrorMessage;
import com.aliaksei.tutski.exceptions.UserNotFoundException;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/messages/{id}")
    public Message getOneMessage(@PathVariable Integer id) {
        return messageService.getMessage(id);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping("/{userName}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Message postNewMessage(@PathVariable String userName, @RequestBody Message message)
            throws UserNotFoundException {
        return messageService.createMessageForUser(message, userName);
    }

    @GetMapping({ "/{userName}/messages", "/{userName}" })
    public List<Message> getAllUserMessages(@PathVariable String userName) throws UserNotFoundException {
        return messageService.getAllUserMessages(userName);
    }

    @GetMapping({ "/{userName}/timeline", "/{userName}/follow" })
    public List<Message> getAllFollowingMessages(@PathVariable String userName) throws UserNotFoundException {
        return messageService.getAllFollowingUserMessages(userName);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorMessage notFoundException(UserNotFoundException exception) {
        ErrorMessage em = new ErrorMessage();
        em.setErrorMessage(exception.getMessage());
        return em;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorMessage validationFail(ConstraintViolationException exception) {
        ErrorMessage em = new ErrorMessage();
        em.setErrorMessage(exception.getMessage());
        return em;
    }
}
