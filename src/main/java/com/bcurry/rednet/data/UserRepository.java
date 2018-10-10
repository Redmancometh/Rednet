package com.bcurry.rednet.data;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.bcurry.rednet.model.User;

@Component
public interface UserRepository extends JpaRepository<User, UUID> {
	public User findUserByUsername(String username);

}
