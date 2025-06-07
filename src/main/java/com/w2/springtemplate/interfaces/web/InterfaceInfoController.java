package com.w2.springtemplate.interfaces.web;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.w2.springtemplate.infrastructure.repository.InterfaceInfoRepository;
import com.w2.springtemplate.model.InterfaceInfo;


import lombok.extern.slf4j.Slf4j;

@Tag(name = "接口管理")
@RestController
@RequestMapping("api/interface-info")
@Slf4j
public class InterfaceInfoController {

    private final InterfaceInfoRepository repository;

    public InterfaceInfoController(InterfaceInfoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public ResponseEntity<InterfaceInfo> save() {
        InterfaceInfo info = new InterfaceInfo();
        info.setName("测试");
        info.setUrl("https:aaa.cc");
        info.setMethod("xxxxxx");
        info.setDescription("ddddddd");
        repository.save(info);
        return ResponseEntity.ok(info);
    }

    @Operation(summary = "查询所有接口数据")
    @PostMapping("/queryAll")
    public ResponseEntity<List<InterfaceInfo>> queryAll() {
        // QInterfaceInfo qInterfaceInfo = QInterfaceInfo.interfaceInfo;
        // QSysUser qSysUser = QSysUser.sysUser;
        // Predicate predicate = qInterfaceInfo.createUserId==((String)qSysUser.id);
        // return ResponseEntity.ok(Lists.newArrayList(repository.findAll(predicate)));
        return ResponseEntity.ok(Lists.newArrayList(repository.findAll()));
    }
}
