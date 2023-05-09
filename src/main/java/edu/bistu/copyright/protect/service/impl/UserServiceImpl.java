package edu.bistu.copyright.protect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.mapper.UserMapper;
import edu.bistu.copyright.protect.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
