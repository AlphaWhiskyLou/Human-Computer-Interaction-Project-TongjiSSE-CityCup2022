package
        com.banking.projectserver.controller;

import cn.hutool.jwt.JWTUtil;
import com.banking.projectserver.aop.log.WebLog;
import com.banking.projectserver.entity.User;
import com.banking.projectserver.entity.unityUser;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO:用户controller类
 *
 * @author wangling
 * @date 2021/11/22 23:19
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @WebLog
    @PostMapping ("login")
    public Response login(@RequestParam String username,@RequestParam String password){
        //System.out.println(username+" "+password);
        User user=userService.getUserById(username);
        //System.out.println(user.toString());
        if(user!=null&&user.getUserPassword().equals(password)){
            Map<String, Object> map = new HashMap<String, Object>() {
                private static final long serialVersionUID = 1L;
                {
                    put("uid", username);
                    put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
                }
            };
            String token = JWTUtil.createToken(map, "1234".getBytes());
            return Response.OK().data("token",token);
        }else{
            return Response.invalidUser().message("用户名或密码错误!");
        }
    }

    @WebLog
    @PostMapping("register")
    public Response register(@RequestParam String userID, @RequestParam String userPassword, @RequestParam String name, @RequestParam String gender, @RequestParam int age){
        User user=userService.getUserById(userID);
        if(user!=null) return Response.invalidUser().message("该身份证号已经被注册！");
        else{
            boolean flag=userService.insertUser(userID, userPassword, name, gender, age);
            if(flag==true) {
                Map<String, Object> map = new HashMap<String, Object>() {
                    private static final long serialVersionUID = 1L;
                    {
                        put("uid", userID);
                        put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
                    }
                };
                String token = JWTUtil.createToken(map, "1234".getBytes());
                return Response.OK().data("token", token);
            }
            else return Response.unknownError().message("新用户创建失败");
        }
    }

    @GetMapping("getUser")
    public Response getUserName(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userID = (String) JWTUtil.parseToken(token).getPayload("uid");
        return Response.OK().data("userName",userID);
    }

    /**
    * 通过用户id获取用户信息接口
    * @param userId 1
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-22 23:25
    */
    @GetMapping("getUserById/{userId}")
    public Response getUserById(@PathVariable String userId){
        User user=userService.getUserById(userId);
        if(user==null){
            return Response.unknownError().message("用户不存在");
        }else{
            return Response.OK().data("user",user);
        }
    }
    @PutMapping("deleteUser/{userId}")
    public Response deleteUser(@PathVariable String userId){
        if(userService.deleteUser(userId)){
            return Response.OK().message("删除成功");
        }
        else {
            return Response.unknownError().message("删除失败");
        }
    }

}
