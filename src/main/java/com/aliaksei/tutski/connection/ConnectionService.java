package com.aliaksei.tutski.connection;

import java.util.List;

import com.aliaksei.tutski.user.User;

public interface ConnectionService {

	/*
	 * Adds connection on specified user
	 * @param User user - user for which we are adding connection
	 * @param User userToFollow - user we would like to follow
	 */
	void addConnection(User user, User userToFollow);

	/*
	 * Gets all connections for specified user
	 * @param User user - user for which connections are collected
	 * @return List<Connection> - list with all connections for specified user
	 */
	List<Connection> getConnectionsForUser(User user);

}