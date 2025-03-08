package com.w2.springtemplate.framework.shiro.bcrypt;

import org.apache.shiro.authc.credential.PasswordService;

import com.w2.springtemplate.utils.crypto.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Deprecated(forRemoval = true)
@Slf4j
public class BCryptPasswordService implements PasswordService {

	private final PasswordEncoder passwordEncoder;

	public BCryptPasswordService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encryptPassword(Object o) throws IllegalArgumentException {
		return passwordEncoder.encode((CharSequence) o);
	}

	@Override
	public boolean passwordsMatch(Object o, String s) {
		log.info("用户密码:{}", o);
		log.info("密文:{}", s);
		return passwordEncoder.matches(new String((char[]) o), s);
	}
}
