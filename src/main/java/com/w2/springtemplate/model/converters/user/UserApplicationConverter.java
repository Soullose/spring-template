package com.w2.springtemplate.model.converters.user;

import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.w2.springtemplate.model.dto.RegisterUserDTO;
import com.w2.springtemplate.model.params.RegisterSysUserParams;

@Mapper
public interface UserApplicationConverter {
    UserApplicationConverter INSTANCE = Mappers.getMapper(UserApplicationConverter.class);

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
            @Mapping(target = "idCard",source = "po.idCard"),
            @Mapping(target = "username",source = "po.username"),
    })
    RegisterUserDTO fromPO(SysUser po);
}
