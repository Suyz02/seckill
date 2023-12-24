package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.exception.GlobalException;
import com.xxxx.seckill.mapper.UserMapper;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.util.CookieUtil;
import com.xxxx.seckill.util.UUIDUtil;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean dologin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        User user = userMapper.selectById(mobile);
        if (null == user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if (!password.equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //用户登录成功，创建session
        //生成sessionid
        String ticket = UUIDUtil.uuid();
        //将创建好的session放入redis存储
        redisTemplate.opsForValue().set("user:"+ticket,user);
        //将session放入客户端中
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success(ticket);
    }

    //将客户端中cookie里的sessionid取出来
    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response){
        if (!StringUtils.hasLength(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:"+userTicket);
        if (user != null){
            //每次请求之后重置session过期时间
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }
}
