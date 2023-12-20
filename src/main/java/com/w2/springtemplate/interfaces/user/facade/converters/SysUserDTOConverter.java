package com.w2.springtemplate.interfaces.user.facade.converters;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import com.w2.springtemplate.model.params.RegisterSysUserParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysUserDTOConverter {

    SysUserDTOConverter INSTANCE = Mappers.getMapper(SysUserDTOConverter.class);

    @Mappings({
            @Mapping(target = "name",source = "params.name"),
            @Mapping(target = "username",source = "params.username"),
            @Mapping(target = "password",source = "params.password"),
            @Mapping(target = "email",source = "params.email"),
            @Mapping(target = "phone",source = "params.phone"),
            @Mapping(target = "address",source = "params.address"),
            @Mapping(target = "idCard",source = "params.idCard"),
    })
    RegisterUserDTO toDTO(RegisterSysUserParams params);

    @Mappings({
            @Mapping(target = "id",source = "po.id"),
            @Mapping(target = "name",source = "po.name"),
            @Mapping(target = "username",source = "po.username"),
            @Mapping(target = "password",source = "po.password"),
            @Mapping(target = "email",source = "po.email"),
            @Mapping(target = "phone",source = "po.phone"),
            @Mapping(target = "idCard",source = "po.idCard"),
    })
    SysUserDTO fromPO(SysUser po);
}
