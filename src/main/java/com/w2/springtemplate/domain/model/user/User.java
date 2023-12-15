package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.domain.experimental.Entity;
import lombok.Data;

@Data
public class User implements Entity<User> {

	private String id;
	private String name;
	private String idCard;
	private String username;

	@Override
	public boolean sameIdentityAs(User other) {
		return other != null && this.idCard != null && this.idCard.equals(other.idCard);
	}
}
