package com.bcurry.rednet.model;

import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bcurry.rednet.model.identifier.Address;
import com.bcurry.rednet.model.identifier.Email;
import com.bcurry.rednet.model.identifier.PhoneNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Brendan T Curry
 *
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Table()
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Address> addresses;
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Email> emails;
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<PhoneNumber> phones;
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	private java.util.Date dob;
}
