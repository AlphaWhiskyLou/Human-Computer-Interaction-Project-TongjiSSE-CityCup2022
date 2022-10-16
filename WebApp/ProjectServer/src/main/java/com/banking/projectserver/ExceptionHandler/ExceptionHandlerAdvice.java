package com.banking.projectserver.ExceptionHandler;


import com.banking.projectserver.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


/**
 * 系统定义的异常处理
 *
 * @author 孟繁霖
 * @date 2021/11/16 16:03
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    // 参数格式异常处理
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response badRequestException(IllegalArgumentException exception) {
        return Response.unknownError().code(HttpStatus.BAD_REQUEST.value()).message("参数格式不合法!");
    }

    // 空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleTypeMismatchException(NullPointerException ex) {
        return  Response.unknownError().code(500).message("空值异常");
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleIOexception(Exception ex){
        return Response.unknownError().message("IO异常");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleUnexpectedServer(Exception ex) {
        return  Response.unknownError().code(500).message("系统发生异常，请联系管理员!");
    }


    // 系统异常处理
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response exception(Throwable throwable) {
        return Response.unknownError().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("系统异常，请联系管理员!");
    }



}
