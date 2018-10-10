package com.bcurry.rednet.data;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bcurry.rednet.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;

/**
 * 
 * @author Brendan T CUrry
 *
 */
@Component
public class UserManager implements UserDetailsService {
	public UserManager() {
		super();
	}

	@Getter
	private Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).setPrettyPrinting().create();

	@Autowired
	private UserRepository users;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AutowireCapableBeanFactory factory;

	@PostConstruct
	public void saveRedman() {
		User user = new User("Redmancometh", encoder.encode("enter1"));
		Set<String> groupSet = new HashSet();
		groupSet.add("admin");
		user.setUserGroups(groupSet);
		factory.autowireBean(user);
		if (users.findUserByUsername("Redmancometh") == null) {
			users.save(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails details = users.findUserByUsername(username);
		factory.autowireBean(details);
		if (details == null)
			throw new UsernameNotFoundException("No user found with this data");
		details.getAuthorities().forEach(authority -> System.out.println("Auth: " + authority));
		return details;
	}

}
