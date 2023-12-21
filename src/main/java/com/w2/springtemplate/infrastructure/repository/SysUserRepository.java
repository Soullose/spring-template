package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.converters.SysUserConverter;
import com.w2.springtemplate.infrastructure.entities.QSysUser;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface SysUserRepository extends BaseJpaRepository<SysUser, String>, SysUserService {

	/// 注册
	default SysUser register(RegisterUserDTO user) {
		SysUser sysUser = SysUserConverter.INSTANCE.fromRegDTO(user);
		return save(sysUser);
	}

	default List<User> findAllUser() {
		List<SysUser> all = this.findAll();
		return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
	}

	default SysUser findOneByUsername(String username) {
		QSysUser qSysUser = QSysUser.sysUser;
		return findOne(qSysUser.username.eq(username)).orElseThrow(EntityNotFoundException::new);
	}

	default SysUser update(UpdateUserDTO user) {
		return null;
	}
}
