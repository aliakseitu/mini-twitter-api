package com.aliaksei.tutski.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
	User findOneByUserName(String name);
}
