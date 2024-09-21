package com.w2.springtemplate.controller.test;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author wsfzj 2024/9/2
 * @version 1.0
 * @description 组织接口
 */
@Data
public class WCIdentityResultOrg {
	private boolean success;
	private List<WCOrgData> data = Lists.newArrayList();
	private String errorCode;
	private String errorName;
	private String errorMessage;
	private WCErrorException errorException;

	/// 组织数据
//    public static class OrgData {
//        private String treeId;
//        private String parentId;
//        private String name;
//        private String type;
//        private String code;
//    }

	/// 错误异常
    @Data
    public static class WCErrorException {
        private String name;
        private String message;
        private String trace;
    }
}
