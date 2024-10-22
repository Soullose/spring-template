package com.w2.springtemplate.framework.event.guava;

import lombok.*;

/**
 * @author wsfzj 2024/10/22
 * @version 1.0
 * @description 测试事件对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TestEvent extends W2Event{
    @Override
    public String type() {
        return "测试";
    }

    private String code;
    private String name;

}
