package com.w2.springtemplate.interfaces.web.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wsfzj 2024/9/2
 * @version 1.0
 * @description 武船组织数据
 */
@Data
public class WCOrgData {
    private String treeId;
    private String parentId;
    private String organizationId;
    private String code;
    private String name;
    private String fullname;
    private String description;
    private Integer sequence;
    /// 解决jackson序列化时，boolean类型
    @JsonProperty("isDisabled")
    private boolean disabled;
    /// 解决jackson序列化 日期类型
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    /// 解决jackson序列化 日期类型
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
    private String unitCode;
    private String orgLeve;
    private String _AID;
    private String _BID;
    private String _ID;
    private String type;
    private String managerId;



}
