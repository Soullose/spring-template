package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.converters.SysUserConverter;
import com.w2.springtemplate.infrastructure.entities.QSysUser;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

	private final SysUserRepository sysUserRepository;

	public SysUserServiceImpl(SysUserRepository sysUserRepository) {
		this.sysUserRepository = sysUserRepository;
	}

	@Override
	public SysUser register(RegisterUserDTO user) {
		SysUser sysUser = SysUserConverter.INSTANCE.fromRegDTO(user);
		return sysUserRepository.save(sysUser);
	}

	@Override
	public SysUser update(UpdateUserDTO user) {
		SysUser sysUser = sysUserRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
		BeanUtils.copyProperties(user, sysUser);
		return sysUserRepository.save(sysUser);
	}

	@Override
	public SysUser findOneByUsername(String username) {
		QSysUser qSysUser = QSysUser.sysUser;
		return sysUserRepository.findOne(qSysUser.username.eq(username)).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public List<User> findAllUser() {
		List<SysUser> all = sysUserRepository.findAll();
		return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
	}
}
