package com.workspace.server.controller

import com.workspace.server.dao.User
import com.workspace.server.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by CHENMA11 on 12/29/2017.
 */

@Controller
public class UserController {

    @Autowired UserDao userDao

    @GetMapping("/greeting")
    public String greetingSubmit(Map<String,Object> map) {

//        String userId;
        User user = userDao.findByEmail('ggg')
        map['greeting'] = user
        return 'hello'
    }



}