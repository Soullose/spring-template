package com.w2.springtemplate.controller.test;

import java.util.List;

import com.w2.springtemplate.demo.WCErrorException;
import lombok.Data;

/**
 * @author wsfzj 2024/9/2
 * @version 1.0
 * @description 用户接口
 */
@Data
public class WCIdentityResultUser {
	private boolean success;
	private List<WCUserData> data;
	private String errorCode;
	private String errorName;
	private String errorMessage;
	private WCErrorException errorException;

	/// 用户信息
//    @Data
//    public static class UserData {
//        private String userId;
//        private String organizationId;
//        private String username;
//        private String password;
//        private String fullname;
//        private boolean isDisabled;// 账号启用禁用的业务要做处理 true:禁用， false:启用
//        private boolean isLocked;// 账号是否被锁定 true:锁定， false:未锁定
//        private LocalDateTime createAt;
//        private LocalDateTime updateAt;
//        private boolean isSystem;
//        private boolean isPublic;
//        private boolean isMaster;
//        private String managerId;
//        private String Job;
//        private String JobGrade;
//        private String Status;
//        private LocalDateTime entryDate;
//        private String _AID;
//        private String _BID;
//        private String _ID;
//    }

	/// 错误异常
//    @Data
//    public static class ErrorException {
//        private String name;
//        private String message;
//        private String trace;
//    }
}
