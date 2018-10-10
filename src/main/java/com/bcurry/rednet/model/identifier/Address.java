package com.bcurry.rednet.model.identifier;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	private String city, state, country, addressLineOne, addressLineTwo;
}
