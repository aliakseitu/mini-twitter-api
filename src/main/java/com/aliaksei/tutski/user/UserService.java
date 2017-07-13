package com.aliaksei.tutski.user;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.aliaksei.tutski.exceptions.UserNotFoundException;


public interface UserService {

	User getUser(String userName) throws UserNotFoundException;
	User getUserOrCreateNew(String userName) throws UserNotFoundException;
	void addUserToFollow(String userName, User userToFollow) throws UserNotFoundException;
	Set<User> getFollowingUsersForUser(User user) throws UserNotFoundException;
}
