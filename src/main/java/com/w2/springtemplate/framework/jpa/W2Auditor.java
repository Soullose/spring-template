package com.w2.springtemplate.framework.jpa;

import com.w2.springtemplate.framework.handlers.login.LoggedInUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class W2Auditor implements AuditorAware<String> {

	 @NonNull
	@Override
	public Optional<String> getCurrentAuditor() {
		LoggedInUser loggedInUser = (LoggedInUser) SecurityUtils.getSubject().getPrincipal();
		if (loggedInUser == null) {
			return Optional.of("unknown");
		}
		return Optional.of(loggedInUser.getId());
	}
}
