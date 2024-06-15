package com.w2.springtemplate.domain.model.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.converters.SysUserConverter;
import com.w2.springtemplate.infrastructure.entities.QSysUser;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	/// 默认密码
	private final static String DEFAULT_PASSWORD = "123456";

	// private final PasswordEncoder passwordEncoder;

	private final PasswordService passwordService;

	private final SysUserRepository sysUserRepository;

	private final EntityManager em;

	protected final JPAQueryFactory queryFactory;

	public SysUserServiceImpl(PasswordService passwordService, SysUserRepository sysUserRepository,EntityManager em) {
		this.passwordService = passwordService;
		this.sysUserRepository = sysUserRepository;
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	/**
	 * 注册用户
	 * 
	 * @param user
	 *            领域DTO
	 * @return {@link SysUser}
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysUser register(RegisterUserDTO user) {
		SysUser sysUser = SysUserConverter.INSTANCE.fromRegDTO(user);
		return sysUserRepository.save(sysUser);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 *            领域DTO
	 * @return {@link SysUser}
	 *         Isolation.READ_COMMITTED-读已提交，即能够读到那些已经提交的数据，自然能够防止脏读，但是无法限制不可重复读和幻读
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public SysUser update(UpdateUserDTO user) {
		SysUser sysUser = sysUserRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
		log.debug("update:{}", SysUserConverter.INSTANCE.updateUserDTOFromPO(user, sysUser));
		return sysUserRepository.save(SysUserConverter.INSTANCE.updateUserDTOFromPO(user, sysUser));
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 *            用户名
	 * @return {@link SysUser}
	 */
	@Cacheable(value = "sysUser", key = "#username")
	@Override
	public SysUser findOneByUsername(String username) {
		QSysUser qSysUser = QSysUser.sysUser;
		return sysUserRepository.findOne(qSysUser.username.eq(username)).orElseThrow(EntityNotFoundException::new);
	}

	/**
	 * 查询所有用户
	 * 
	 * @return {@link List<User>}
	 */
	@Override
	public List<User> findAllUser() {
		List<SysUser> all = sysUserRepository.findAll();
		return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
	}

	/**
	 * 重置用户密码
	 *
	 * @param id
	 *            用户ID
	 * @return {@link SysUser}
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public SysUser resetPassword(String id) {
		SysUser sysUser = sysUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		sysUser.setPassword(passwordService.encryptPassword(DEFAULT_PASSWORD));
		return sysUserRepository.save(sysUser);
	}

	/**
	 * 修改用户密码
	 *
	 * @param id
	 *            用户ID
	 * @param newPassword
	 *            用户新密码
	 * @return {@link SysUser}
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public SysUser changeSysUserPassword(String id, String newPassword) {
//		SysUser sysUser = sysUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//		sysUser.setPassword(passwordService.encryptPassword(newPassword));

		QSysUser qSysUser = QSysUser.sysUser;
		this.queryFactory.update(qSysUser).where(qSysUser.id.eq(id))
				.set(qSysUser.password, passwordService.encryptPassword(newPassword)).execute();
//		return sysUserRepository.save(sysUser);
		return sysUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
