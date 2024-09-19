package com.w2.springtemplate.controller.test;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/2
 * @version 1.0
 * @description 武船组织数据
 */
@Data
@Builder
public class WCOrgData {
    private String treeId;
    private String parentId;
    private String organizationId;
    private String code;
    private String name;
    private String fullname;
    private String description;
    private Integer sequence;
    private boolean isDisabled;
    private Date createAt;
    private Date updateAt;
    private String orgLeve;
    private String _AID;
    private String _BID;
    private String _ID;
    private String type;
    private String managerId;


}
