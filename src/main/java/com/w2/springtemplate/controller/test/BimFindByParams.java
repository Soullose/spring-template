package com.w2.springtemplate.controller.test;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/19
 * @version 1.0
 * @description 武船身份治理查找params
 */
@Data
@Builder
public class BimFindByParams {
    private String token;
    private Search _search;
    private Page _page;

    @Data
    private static class Search {

    }

    @Data
    private static class Page {
        private int size;
        private int number;
    }
}
