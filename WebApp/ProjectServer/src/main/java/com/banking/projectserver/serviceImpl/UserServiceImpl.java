package
        com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.User;
import com.banking.projectserver.mapper.UserMapper;
import com.banking.projectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 *
 * @author 孟繁霖
 * @date 2021/11/22 23:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public boolean insertUser(String userId, String userPassword,String name,String gender,int age){
        return userMapper.insertUser(userId, userPassword, name, gender, age);
    }

    @Override
    public boolean deleteUser(String userId){
        return userMapper.deleteUser(userId);
    }
}
