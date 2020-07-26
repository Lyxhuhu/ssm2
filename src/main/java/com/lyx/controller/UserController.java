package com.lyx.controller;

import com.lyx.bean.PageInfo;
import com.lyx.bean.Role;
import com.lyx.bean.User;
import com.lyx.service.IRoleService;
import com.lyx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @RequestMapping("/login.do")
    public ModelAndView login(User user,HttpSession session){
        int id=iUserService.login(user.getUsername(),user.getPassword());
        ModelAndView modelAndView=new ModelAndView();
        if(id!=-1){
            List<Integer> roleIds=iRoleService.findRoleByUserId(id);
            session.setAttribute("roleIds",roleIds);
            session.setAttribute("user",user);
            modelAndView.setViewName("main");
        }else{
            modelAndView.setViewName("../failer");
        }
        return modelAndView;
    }
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(defaultValue = "1") int currentPage, String username,@RequestParam(defaultValue = "0")  int type,HttpSession session){
        if(type==1){
            session.setAttribute("searchname",username);
        }else if(type==0){
            username= (String) session.getAttribute("searchname");
        }else if(type==2){
            session.removeAttribute("searchname");
        }
        PageInfo<User> pageInfo=iUserService.findAll(currentPage,username);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("user-list");
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;

    }
    @RequestMapping("/deleteById.do")
    public String delete(int id){
        iUserService.deleteById(id);

        return "redirect:/user/findAll.do";
    }
    @RequestMapping("/add.do")
    public String add(User user){
        iUserService.add(user);
        return "redirect:/user/findAll.do";
    }
    @RequestMapping("/toUpdate.do")
    public ModelAndView toUpdate(int id){
        User user=iUserService.selectUserById(id);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("user-update");
        return modelAndView;
    }
    @RequestMapping("/update.do")
    public String update(User user){
        iUserService.update(user);
        return "redirect:/user/findAll.do";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "../login";
    }
    @RequestMapping("/toAddRole.do")
    public ModelAndView toAddRole(int id){
       List<Role> roles= iRoleService.findNotRoleByUserId(id);
       ModelAndView modelAndView=new ModelAndView();
       modelAndView.addObject("roles",roles);
       modelAndView.setViewName("user-role-add");
       modelAndView.addObject("id",id);
       return modelAndView;
    }

    @RequestMapping("/addRole.do")
    public String addRole(String roleIds,String userId){
        String[] strs=roleIds.split(",");
        List<Integer> ids=new ArrayList<>();
        for(String s:strs){
            ids.add(Integer.parseInt(s));
        }
        iRoleService.addRole(ids,Integer.parseInt(userId));
        return "redirect:/user/findAll.do";
    }

    @RequestMapping("/deleteAll.do")
    @ResponseBody
    public String deleteAll(String userList){
        String[] strings=userList.split(",");
        List<Integer> ids=new ArrayList<>();
        for(String s:strings){
            ids.add(Integer.parseInt(s));
        }
        iUserService.deleteAll(ids);
        return "";
    }

}
