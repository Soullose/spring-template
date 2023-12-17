package com.w2.springtemplate.infrastructure.converters;

import com.w2.springtemplate.model.dto.RegisterUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.dto.SysUserRegDTO;

/**
 * 系统用户转换器
 */
@Mapper
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    @Mappings({
            @Mapping(target = "username",source = "dto.username"),
            @Mapping(target = "password",source = "dto.password"),
            @Mapping(target = "name",source = "dto.name")
    })
    SysUser fromDTO(SysUserRegDTO dto);

    @Mappings({
            @Mapping(target = "name",source = "dto.name"),
            @Mapping(target = "username",source = "dto.username"),
            @Mapping(target = "password",source = "dto.password"),
            @Mapping(target = "idCard",source = "dto.idCard"),
    })
    SysUser fromRegDTO(RegisterUserDTO dto);


    @Mappings({
            @Mapping(target = "id",source = "po.id"),
            @Mapping(target = "name",source = "po.name"),
            @Mapping(target = "idCard",source = "po.idCard"),
            @Mapping(target = "username",source = "po.username"),
    })
    User fromPO(SysUser po);


//    @Mappings({
//            @Mapping(target = "id",source = "po.id"),
//            @Mapping(target = "name",source = "po.name"),
//            @Mapping(target = "idCard",source = "po.idCard"),
//            @Mapping(target = "username",source = "po.username"),
//    })
//    SysUserRegDTO toDTO(SysUser po);
}
