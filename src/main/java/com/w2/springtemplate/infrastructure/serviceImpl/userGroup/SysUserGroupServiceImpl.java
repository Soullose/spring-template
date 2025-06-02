package com.w2.springtemplate.infrastructure.serviceImpl.userGroup;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.w2.springtemplate.domain.service.userGroup.SysUserGroupService;
import com.w2.springtemplate.infrastructure.entities.QSysUserGroup;
import com.w2.springtemplate.infrastructure.entities.SysUserGroup;
import com.w2.springtemplate.infrastructure.repository.SysUserGroupRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class SysUserGroupServiceImpl implements SysUserGroupService {

	private final static Logger log = LoggerFactory.getLogger(SysUserGroupServiceImpl.class);

	private final EntityManager em;

	protected final JPAQueryFactory queryFactory;

	private final SysUserGroupRepository sysUserGroupRepository;

	public SysUserGroupServiceImpl(EntityManager em,SysUserGroupRepository sysUserGroupRepository) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.sysUserGroupRepository = sysUserGroupRepository;
	}

	@Override
	@Transactional
	public SysUserGroup createSysUserGroup(SysUserGroup sysUserGroup) {
		QSysUserGroup qSysUserGroup = QSysUserGroup.sysUserGroup;


//		this.queryFactory.selectFrom(qSysUserGroup).select();
		log.debug("查询1:{}",this.queryFactory.selectFrom(qSysUserGroup).fetch());
		log.debug("查询:{}", this.queryFactory.select(qSysUserGroup).from(qSysUserGroup).fetch());
		this.queryFactory.delete(qSysUserGroup).where(qSysUserGroup.id.eq("1")).execute();

		return null;
	}

	@Override
	public SysUserGroup updateSysUserGroup(SysUserGroup sysUserGroup) {
		return null;
	}

	@Override
	public SysUserGroup getSysUserGroupById(String id) {
		return null;
	}

	@Override
	public void deleteSysUserGroup(String id) {

	}
}
