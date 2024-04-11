package com.w2.springtemplate.model.service;

import com.w2.springtemplate.infrastructure.repository.InterfaceInfoRepository;
import com.w2.springtemplate.model.InterfaceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class InterfaceInfoService {

    @Autowired
    private InterfaceInfoRepository repository;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void test1(){

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setName("接口名称");
        interfaceInfo.setUrl("http://localhost:8080/api/test");
        interfaceInfo.setMethod("GET");
        interfaceInfo.setDescription("接口描述");
        interfaceInfo.setRequestHeader("Content-Type: application/json");
        interfaceInfo.setResponseHeader("Content-Type: application/json");
        interfaceInfo.setStatus(false);
        interfaceInfo.setDelete(false);

        repository.save(interfaceInfo);
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void test2(){

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setName("接口名称123");
        interfaceInfo.setUrl("http://localhost:8080/api/test");
        interfaceInfo.setMethod("GET123");
        interfaceInfo.setDescription("接口描述123");
        interfaceInfo.setRequestHeader("Content-Type: application/json");
        interfaceInfo.setResponseHeader("Content-Type: application/json");
        interfaceInfo.setStatus(false);
        interfaceInfo.setDelete(false);

        repository.save(interfaceInfo);
        throw new RuntimeException("xxxxxxxxxxxxx");
    }
}
