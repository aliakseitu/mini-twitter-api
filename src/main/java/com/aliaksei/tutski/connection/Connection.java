package com.aliaksei.tutski.connection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.aliaksei.tutski.user.User;

@Entity
@Table(name="Connections")
public class Connection {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="following_user_id", referencedColumnName = "id")
	private User followingUser;
	
	public Connection() {
	}
	
	public Connection(User user, User userToFollow) {
		this.user = user;
		this.followingUser = userToFollow;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getFollowingUser() {
		return followingUser;
	}
	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}
	
}
