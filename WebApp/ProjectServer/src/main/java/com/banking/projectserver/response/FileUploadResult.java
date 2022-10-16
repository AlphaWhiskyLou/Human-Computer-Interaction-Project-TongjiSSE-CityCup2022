package com.banking.projectserver.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写FileUploadResult类的描述
 *
 * @author 孟繁霖
 * @date 2021/12/1 11:18
 */
@Data
@AllArgsConstructor
public class FileUploadResult {

    private boolean success;
    private String msg;
    private String fileUrl;

    private FileUploadResult(){
    }

    public static  FileUploadResult getFileUploadResult(){
        return new FileUploadResult();
    }

    /**
     * 文件上传成功返回结果
     * @param fileUrl 文件访问路径
     * @return : com.se.easylab.Result.FileUploadResult
     * @author Covenant
     * @date 2021-12-01 11:27
     */
    public FileUploadResult success(String fileUrl){
        this.setSuccess(true);
        this.setFileUrl(fileUrl);
        this.setMsg("上传成功");
        return this;
    }

    /**
     * 文件上传失败返回结果
     * @param msg 错误信息
     * @return : com.se.easylab.Result.FileUploadResult
     * @author Covenant
     * @date 2021-12-01 11:27
     */
    public FileUploadResult fail(String msg){
        this.setMsg(msg);
        this.setSuccess(false);
        this.setFileUrl("");
        return this;
    }
}
