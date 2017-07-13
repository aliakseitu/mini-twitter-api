package com.aliaksei.tutski.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliaksei.tutski.connection.ConnectionService;
import com.aliaksei.tutski.exceptions.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ConnectionService connService;

    public User getUserOrCreateNew(String userName) {
        User user = userRepo.findOneByUserName(userName);
        if (user == null) {
            user = new User(userName);
            user = userRepo.save(user);
        }
        return user;
    }

    private User checkAndGetUser(String userName) throws UserNotFoundException {
        User user = userRepo.findOneByUserName(userName);
        if (user == null) {
            throw new UserNotFoundException("User with name '" + userName + "' not found");
        }

        return user;
    }

    @Override
    public User getUser(String userName) throws UserNotFoundException {
        return checkAndGetUser(userName);
    }

    @Override
    public void addUserToFollow(String userName, User userToFollow) throws UserNotFoundException {
        User user = checkAndGetUser(userName);
        userToFollow = checkAndGetUser(userToFollow.getUserName());

        connService.addConnection(user, userToFollow);
    }

    @Override
    public Set<User> getFollowingUsersForUser(User user) throws UserNotFoundException {
        user = checkAndGetUser(user.getUserName());
        Set<User> set = new HashSet<>();
        connService.getConnectionsForUser(user).forEach(x -> set.add(x.getFollowingUser()));
        return set;
    }

}
