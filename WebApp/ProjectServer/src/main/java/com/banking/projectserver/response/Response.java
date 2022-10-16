package
        com.banking.projectserver.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果封装类，controller统一的返回类型
 *
 * @author 孟繁霖 & 娄天宇
 * @date 2021/11/22 22:59
 */
@Data
public class Response {

    private boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private Response(){
    }

    public static  Response getResponse(ResponseCodeEnum responseCodeEnum){
        Response response=new Response();
        response.setCode(responseCodeEnum.getCode());
        response.setSuccess(responseCodeEnum.getSuccess());
        response.setMessage(responseCodeEnum.getMessage());
        return response;
    }


    /**
    * 成功响应
    *
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-22 23:06
    */
    public static Response OK(){
        return getResponse(ResponseCodeEnum.SUCCESS);
    }


    /**
    * 未知错误响应
    *
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-22 23:07
    */
    public static Response unknownError(){
        return getResponse(ResponseCodeEnum.UNKNOWN_ERROR);
    }

    /**
     * @author Tianyu Lou
     * @return com.banking.projectserver.response.Response
     */
    public static Response emptyResponse(){
        return getResponse(ResponseCodeEnum.EMPTY_RESPONSE);
    }

    /**
     * @author Tianyu Lou
     * @return com.banking.projectserver.response.Response
     */
    public static Response invalidUser(){
        return getResponse(ResponseCodeEnum.INVALID_USER);
    }

    /**
     * @author Tianyu Lou
     * @return com.banking.projectserver.response.Response
     */
    public static Response invalidRole(){
        return getResponse(ResponseCodeEnum.INVALID_ROLE);
    }

    /**
     * @author Tianyu Lou
     * @return com.banking.projectserver.response.Response
     */
    public static Response invalidPassword(){
        return getResponse(ResponseCodeEnum.INVALID_PASSWORD);
    }

    /**
     * @author Tianyu Lou
     * @return com.banking.projectserver.response.Response
     */
    public static Response invalidService(){
        return getResponse(ResponseCodeEnum.INVALID_SERVICE);
    }

    public Response data(Map<String,Object>map){
        this.setData(map);
        return this;
    }

    public Response data(String key,Object value){
        this.data.put(key, value);
        return this;
    }

    public Response message(String message){
        this.setMessage(message);
        return this;
    }

    public Response code(Integer code){
        this.setCode(code);
        return this;
    }

    public Response success(boolean success){
        this.setSuccess(success);
        return this;
    }


}
