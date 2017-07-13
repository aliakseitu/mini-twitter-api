package com.aliaksei.tutski.connection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliaksei.tutski.user.User;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	ConnectionRepository connectionRepo;
	
	@Override
	public void addConnection(User user, User userToFollow){
		Connection conn = new Connection(user, userToFollow);
		connectionRepo.save(conn);
	}
	
	@Override
	public List<Connection> getConnectionsForUser(User user){
		return connectionRepo.findByUser(user);
	}
}
