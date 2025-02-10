package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.framework.jpa.CustomRepository;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface SysUserRepository extends BaseJpaRepository<SysUser, String> {

	/// 注册
//	default SysUser register(RegisterUserDTO user) {
//		SysUser sysUser = SysUserConverter.INSTANCE.fromRegDTO(user);
//		return save(sysUser);
//	}
//
//	default List<User> findAllUser() {
//		List<SysUser> all = this.findAll();
//		return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
//	}
//
//	default SysUser findOneByUsername(String username) {
//		QSysUser qSysUser = QSysUser.sysUser;
//		return findOne(qSysUser.username.eq(username)).orElseThrow(EntityNotFoundException::new);
//	}
//
//	default SysUser update(UpdateUserDTO user) {
//		SysUser sysUser = findById(user.getId()).orElseThrow(EntityNotFoundException::new);
//		BeanUtils.copyProperties(user,sysUser);
//		return save(sysUser);
//	}


}
