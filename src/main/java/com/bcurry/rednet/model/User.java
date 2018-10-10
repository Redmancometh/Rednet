package com.bcurry.rednet.model;

import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bcurry.rednet.config.pojo.SecurityConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class User extends Person implements UserDetails {
	private static final long serialVersionUID = -1212577542796447737L;
	@Column(unique = true)
	@NonNull
	private String username;
	@NonNull
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	@Embedded
	private Set<String> userGroups;
	@Autowired
	@Qualifier("sec-cfg")
	@Transient
	protected SecurityConfig groups;

	public String personData() {
		return super.toString();
	}

	@PrePersist
	public void prePersist() {
		for (int x = 0; x < 50; x++)
			System.out.println("PRE PERSIST");
	}

	/**
	 * Get the raw string representations of the users groups. This method is NOT to
	 * be used in regard to Spring. getAuthorities should always be called unless
	 * you are directly editing groups in the DAO.
	 * 
	 * Further, permissions should never be changed via the returned Collection from
	 * getAuthorities.
	 * 
	 * TL;DR getAuthorities to read perms getUserGroups to read groups.
	 * getUserGroups to edit DAO
	 * 
	 * @return
	 */
	public Set<String> getUserGroups() {
		return userGroups;
	}

	/**
	 * This is the method used by string to get the overall permissions of a
	 * LabratUser.
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		userGroups.forEach((group) -> System.out.println("Group: " + group));
		System.out.println("GROUPS: " + (groups == null));
		return groups.nodesForGroups(userGroups);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
