package com.w2.springtemplate.controller.test;

import lombok.Data;

/**
 * @author wsfzj 2024/9/19
 * @version 1.0
 * @description 组织数量params
 */
@Data
public class BimCountParams {
    private String token;
    private _search _search;

    @Data
    private static class _search {

    }
}
