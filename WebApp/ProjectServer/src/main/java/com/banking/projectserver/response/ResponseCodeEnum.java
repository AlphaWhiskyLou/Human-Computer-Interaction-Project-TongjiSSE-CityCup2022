package com.banking.projectserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 响应结果状态枚举类
*
* @author Covenant
* @date 2021-11-22 22:58
*/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    //自定义部分响应结果
    SUCCESS(true,200,"成功"),
    UNKNOWN_ERROR(false,201,"未知错误"),
    EMPTY_RESPONSE(false,202,"没有信息"),
    INVALID_USER(false,503,"无效用户"),
    INVALID_ROLE(false,204,"无效角色"),
    INVALID_PASSWORD(false,205,"无效密码"),
    INVALID_SERVICE(false,501,"无效服务");


    private Boolean success;
    private Integer code;
    private String message;
}
