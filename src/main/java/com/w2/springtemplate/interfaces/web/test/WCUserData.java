package com.w2.springtemplate.interfaces.web.test;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/2
 * @version 1.0
 * @description 武船用户数据
 */
@Data
@Builder
public class WCUserData {
    private String userId;
    private String organizationId;
    private String username;
    private String password;
    private String fullname;
    private boolean isDisabled;// 账号启用禁用的业务要做处理 true:禁用， false:启用
    private boolean isLocked;// 账号是否被锁定 true:锁定， false:未锁定
    private Date createAt;
    private Date updateAt;
    private boolean isSystem;
    private boolean isPublic;
    private boolean isMaster;
    private String managerId;
    private String Job;
    private String JobGrade;
    private String Status;
    private Date entryDate;
    private String _AID;
    private String _BID;
    private String _ID;

//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getOrganizationId() {
//        return organizationId;
//    }
//
//    public void setOrganizationId(String organizationId) {
//        this.organizationId = organizationId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    public boolean isDisabled() {
//        return isDisabled;
//    }
//
//    public void setDisabled(boolean disabled) {
//        isDisabled = disabled;
//    }
//
////    public boolean getIDisabled() {
////        return isDisabled;
////    }
////
////    public void setIDisabled(boolean disabled) {
////        isDisabled = disabled;
////    }
//
//    public boolean getIsLocked() {
//        return isLocked;
//    }
//
//    public void setIsLocked(boolean locked) {
//        isLocked = locked;
//    }
//
//    public Date getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(Date createAt) {
//        this.createAt = createAt;
//    }
//
//    public Date getUpdateAt() {
//        return updateAt;
//    }
//
//    public void setUpdateAt(Date updateAt) {
//        this.updateAt = updateAt;
//    }
//
//    public Boolean getIsSystem() {
//        return isSystem;
//    }
//
//    public void setIsSystem(boolean system) {
//        isSystem = system;
//    }
//
//    public boolean getIsPublic() {
//        return isPublic;
//    }
//
//    public void setIsPublic(boolean aPublic) {
//        isPublic = aPublic;
//    }
//
//    public boolean getIsMaster() {
//        return isMaster;
//    }
//
//    public void setIsMaster(boolean master) {
//        isMaster = master;
//    }
//
//    public String getManagerId() {
//        return managerId;
//    }
//
//    public void setManagerId(String managerId) {
//        this.managerId = managerId;
//    }
//
//    public String getJob() {
//        return Job;
//    }
//
//    public void setJob(String job) {
//        Job = job;
//    }
//
//    public String getJobGrade() {
//        return JobGrade;
//    }
//
//    public void setJobGrade(String jobGrade) {
//        JobGrade = jobGrade;
//    }
//
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String status) {
//        Status = status;
//    }
//
//    public Date getEntryDate() {
//        return entryDate;
//    }
//
//    public void setEntryDate(Date entryDate) {
//        this.entryDate = entryDate;
//    }
//
//    public String get_AID() {
//        return _AID;
//    }
//
//    public void set_AID(String _AID) {
//        this._AID = _AID;
//    }
//
//    public String get_BID() {
//        return _BID;
//    }
//
//    public void set_BID(String _BID) {
//        this._BID = _BID;
//    }
//
//    public String get_ID() {
//        return _ID;
//    }
//
//    public void set_ID(String _ID) {
//        this._ID = _ID;
//    }
//
//
//    @Override
//    public String toString() {
//        return "WCUserData{" +
//                "userId='" + userId + '\'' +
//                ", organizationId='" + organizationId + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", fullname='" + fullname + '\'' +
//                ", isDisabled=" + isDisabled +
//                ", isLocked=" + isLocked +
//                ", createAt=" + createAt +
//                ", updateAt=" + updateAt +
//                ", isSystem=" + isSystem +
//                ", isPublic=" + isPublic +
//                ", isMaster=" + isMaster +
//                ", managerId='" + managerId + '\'' +
//                ", Job='" + Job + '\'' +
//                ", JobGrade='" + JobGrade + '\'' +
//                ", Status='" + Status + '\'' +
//                ", entryDate=" + entryDate +
//                ", _AID='" + _AID + '\'' +
//                ", _BID='" + _BID + '\'' +
//                ", _ID='" + _ID + '\'' +
//                '}';
//    }
}
