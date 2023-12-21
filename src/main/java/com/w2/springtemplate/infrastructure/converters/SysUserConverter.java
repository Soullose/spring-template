package com.w2.springtemplate.infrastructure.converters;

import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

/**
 * 系统用户转换器
 */
@Mapper(nullValueCheckStrategy = ALWAYS)
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);


    @Mappings({
            @Mapping(target = "name",source = "dto.name"),
            @Mapping(target = "username",source = "dto.username"),
            @Mapping(target = "password",source = "dto.password"),
            @Mapping(target = "idCard",source = "dto.idCard"),
            @Mapping(target = "email",source = "dto.email"),
            @Mapping(target = "phone",source = "dto.phone"),
    })
    SysUser fromRegDTO(RegisterUserDTO dto);

    @Mappings({
            @Mapping(target = "id",source = "dto.id"),
            @Mapping(target = "name",source = "dto.name"),
            @Mapping(target = "username",source = "dto.username"),
            @Mapping(target = "password",source = "dto.password"),
            @Mapping(target = "idCard",source = "dto.idCard"),
            @Mapping(target = "email",source = "dto.email"),
            @Mapping(target = "phone",source = "dto.phone"),
    })
    SysUser fromUpdateDTO(UpdateUserDTO dto);


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
