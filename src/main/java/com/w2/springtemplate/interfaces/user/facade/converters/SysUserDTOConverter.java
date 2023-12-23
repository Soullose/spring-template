package com.w2.springtemplate.interfaces.user.facade.converters;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import com.w2.springtemplate.model.params.RegisterSysUserParams;
import com.w2.springtemplate.model.params.UpdateSysUserParams;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(nullValueCheckStrategy = ALWAYS,unmappedTargetPolicy = IGNORE)
public interface SysUserDTOConverter {

	SysUserDTOConverter INSTANCE = Mappers.getMapper(SysUserDTOConverter.class);

	@Mappings({@Mapping(target = "name", source = "params.name"),
			@Mapping(target = "username", source = "params.username"),
			@Mapping(target = "password", source = "params.password"),
			@Mapping(target = "email", source = "params.email"),
			@Mapping(target = "phone", source = "params.phone"),
			@Mapping(target = "address", source = "params.address"),
			@Mapping(target = "idCard", source = "params.idCard"),})
	RegisterUserDTO toDTO(RegisterSysUserParams params);

	@BeanMapping(ignoreByDefault = true)
	@Mappings({
			@Mapping(target = "id", source = "params.id"),
			@Mapping(target = "name", source = "params.name",ignore = true),
			@Mapping(target = "username", source = "params.username"),
			@Mapping(target = "password", source = "params.password"),
			@Mapping(target = "email", source = "params.email"),
			@Mapping(target = "phone", source = "params.phone"),
			@Mapping(target = "address", source = "params.address"),
			@Mapping(target = "idCard", source = "params.idCard"),
	})
	UpdateUserDTO toUpdateDTO(UpdateSysUserParams params);

	@BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
	UpdateUserDTO updateUserDtoFromParams( UpdateSysUserParams params,@MappingTarget UpdateUserDTO dto);

	@Mappings({@Mapping(target = "id", source = "po.id"),
			@Mapping(target = "name", source = "po.name"),
			@Mapping(target = "username", source = "po.username"),
			@Mapping(target = "password", source = "po.password"),
			@Mapping(target = "email", source = "po.email"),
			@Mapping(target = "phone", source = "po.phone"),
			@Mapping(target = "idCard", source = "po.idCard"),})
	SysUserDTO fromPO(SysUser po);
}
