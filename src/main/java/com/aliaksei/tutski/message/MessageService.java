package com.aliaksei.tutski.message;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aliaksei.tutski.exceptions.UserNotFoundException;


public interface MessageService {

	/*
	 * Creates new message for user specified by name
	 */
	Message createMessageForUser(Message message, String userName) throws UserNotFoundException;

	/*
	 * Adds message without user
	 */
	void addMessage(Message message);

	/*
	 * Gets message by id
	 */
	Message getMessage(Integer id);

	/*
	 * Gets all available messages
	 */
	List<Message> getAllMessages();

	/*
	 * Gets messages for user specified by name
	 */
	List<Message> getAllUserMessages(String userName) throws UserNotFoundException;

	/*
	 * Gets list of the messages posted by all the people specified user is follow
	 */
	List<Message> getAllFollowingUserMessages(String userName) throws UserNotFoundException;

}