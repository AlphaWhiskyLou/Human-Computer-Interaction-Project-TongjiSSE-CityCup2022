package
        com.banking.projectserver.controller;

import cn.hutool.core.lang.UUID;
import com.banking.projectserver.response.FileUploadResult;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO:此处写TestController类的描述
 *
 * @author 孟繁霖
 * @date 2021/12/30 2:27
 */
@RestController
public class TestController {

    @Autowired
    private UploadFile uploadFile;

    @PostMapping("file")
    public Response test(@RequestParam("file") MultipartFile file){
        FileUploadResult fileUploadResult = uploadFile.uploadFile(file, "bank", UUID.randomUUID().toString());
        return Response.OK().data("url",fileUploadResult.getFileUrl());
    }

}
