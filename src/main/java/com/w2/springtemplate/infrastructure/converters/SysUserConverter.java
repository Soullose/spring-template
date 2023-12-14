package com.w2.springtemplate.infrastructure.converters;

import com.w2.springtemplate.model.dto.SysUserRegDTO;
import org.mapstruct.Mapper;

import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 系统用户转换器
 */
@Mapper
public interface SysUserConverter {

    @Mappings({
            @Mapping(target = "username",source = "dto.username"),
            @Mapping(target = "password",source = "dto.password"),
            @Mapping(target = "name",source = "dto.name")
    })
    SysUser fromDTO(SysUserRegDTO dto);
}
