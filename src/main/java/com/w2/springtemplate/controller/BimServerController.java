package com.w2.springtemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.w2.springtemplate.controller.test.*;
import com.w2.springtemplate.framework.vfs.ApacheVfsResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wsfzj 2024/9/18
 * @version 1.0
 * @description 武船身份治理测试接口
 */

@Api(tags = "武船身份治理测试接口")
@RestController
@RequestMapping("/bim-server")
@Slf4j
public class BimServerController {
    private final ResourceLoader resourceLoader;

    public BimServerController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @ApiOperation(value = "测试武船身份治理系统登录接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtAuthService/login")
    public ResponseEntity<BimLoginRestParams> testLogin(@RequestBody BimLoginParams params) {
        log.debug("params: {}", params);
        BimLoginRestParams bimLoginRestParams = BimLoginRestParams.builder().success(true)
                .data("00877593-0E2E-471C A87-473768558E8").build();
        return ResponseEntity.ok(bimLoginRestParams);
    }

    @ApiOperation(value = "测试武船身份治理系统退出接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtAuthService/logout")
    public ResponseEntity<BimLogoutRestParams> testLogout(@RequestBody BimLogoutParams params) {
        log.debug("params: {}", params);
        BimLogoutRestParams bimLogoutRestParams = BimLogoutRestParams.builder().success(true).data(true).build();
        return ResponseEntity.ok(bimLogoutRestParams);
    }

    @ApiOperation(value = "测试武船身份治理系统组织数量接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtTargetOrganizationService/countBy")
    public ResponseEntity<BimCountRestParams> testOrgCountWC(@RequestBody BimCountParams params) {
        log.debug("bimOrgCountParams: {}", params);
        BimCountRestParams bimOrgCountRestParams = BimCountRestParams.builder().success(true).data(2480).build();
        return ResponseEntity.ok(bimOrgCountRestParams);
    }

    @ApiOperation(value = "测试武船身份治理系统用户查询接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtTargetAccountService/countBy")
    public ResponseEntity<BimCountRestParams> testUserCountWC(@RequestBody BimCountParams params) {
        log.debug("bimOrgCountParams: {}", params);
        BimCountRestParams bimUserCountRestParams = BimCountRestParams.builder().success(true).data(2480).build();
        return ResponseEntity.ok(bimUserCountRestParams);
    }

    @ApiOperation(value = "测试武船身份治理系统组织查询接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtTargetOrganizationService/findBy")
    public ResponseEntity<WCIdentityResultOrg> testOrgFindByWC(@RequestBody BimFindByParams params) {
        log.debug("bimFindByParams: {}", params);
        String json = Test.org;
        WCIdentityResultOrg wcIdentityResultOrg = gson.fromJson(json, WCIdentityResultOrg.class);
        try {
            WCIdentityResultOrg wcIdentityResultOrg1 = objectMapper.readValue(json, WCIdentityResultOrg.class);
            log.debug("wcIdentityResultOrg1: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wcIdentityResultOrg1));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(this.org());
    }

    @ApiOperation(value = "测试武船身份治理系统用户数量接口")
    @PostMapping(value = "/api/rest/integration/ExtApiIngtTargetAccountService/findBy")
    public ResponseEntity<WCIdentityResultUser> testUserFindByWC(@RequestBody BimFindByParams params) {
        log.debug("bimFindByParams: {}", params);
        String json = Test.user;
        WCIdentityResultUser wcIdentityResultOrg = gson.fromJson(json, WCIdentityResultUser.class);
        return ResponseEntity.ok(this.user());
    }

    private WCIdentityResultOrg org() {

        ApacheVfsResource apacheVfsResource = (ApacheVfsResource) resourceLoader.getResource("vfs://cccccc/org.json");

        List<WCOrgData> wcIdentityResultOrgData = null;
        WCIdentityResultOrg wcIdentityResultOrg = null;
        try {
            File file = apacheVfsResource.getFile();
            String json = FileUtils.readFileToString(file, "UTF-8");
            wcIdentityResultOrg = gson.fromJson(json, WCIdentityResultOrg.class);
//			log.error("武船和身份治理系统组织同步成功2:{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wcIdentityResultOrg));
            wcIdentityResultOrgData = wcIdentityResultOrg.getData();
        } catch (IOException e) {
            log.error("exception message", e);
        }
        return wcIdentityResultOrg;
    }

    private WCIdentityResultUser user() {

        ApacheVfsResource apacheVfsResource = (ApacheVfsResource) resourceLoader.getResource("vfs://cccccc/user.json");

        List<WCUserData> wcIdentityResultUserData = null;
        WCIdentityResultUser wcIdentityResultUser = null;
        try {
            File file = apacheVfsResource.getFile();
            String json = FileUtils.readFileToString(file, "UTF-8");
            wcIdentityResultUser = gson.fromJson(json, WCIdentityResultUser.class);
//			log.error("武船和身份治理系统组织同步成功2:{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wcIdentityResultOrg));
            wcIdentityResultUserData = wcIdentityResultUser.getData();
        } catch (IOException e) {
            log.error("exception message", e);
        }
        return wcIdentityResultUser;
    }
}
