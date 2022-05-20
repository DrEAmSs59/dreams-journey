package com.dreamss.dreamjourneyuser.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dreamss.dreamjourneyuser.dao.UserDao;
import com.dreamss.dreamjourneyuser.entities.User;
import com.dreamss.dreamjourneyuser.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:43
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao{
}
