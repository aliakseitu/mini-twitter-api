package com.aliaksei.tutski.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aliaksei.tutski.exceptions.ErrorMessage;
import com.aliaksei.tutski.exceptions.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@PutMapping("/{userName}/follow")
	public void getFollowingMessages(@PathVariable String userName, @RequestBody User user) throws UserNotFoundException{
		userService.addUserToFollow(userName, user);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ErrorMessage notFoundException(UserNotFoundException exception){
		ErrorMessage em = new ErrorMessage();
		em.setMessage(exception.getMessage());
		return em;
	}
}
