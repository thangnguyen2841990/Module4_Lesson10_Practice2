package com.codegym.controller;

import com.codegym.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {
    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    @GetMapping
    public ModelAndView showFormLogin(@CookieValue(value = "setUser", defaultValue = "") String setUser) {
        Cookie cookie = new Cookie("setUser", setUser);
        ModelAndView modelAndView = new ModelAndView("/user/index");
        modelAndView.addObject("cookieValue", cookie);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute User user, @CookieValue(value = "setUser", defaultValue = "") String setUser,
                                HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/user/index");
        if (user.getEmail().equals("thang@gmail.com") && (user.getPassword().equals("thuthuyda1"))) {
            if (user.getEmail() == null)
                setUser = user.getEmail();
            Cookie cookie = new Cookie("setUser", setUser);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            Cookie[] cookies = request.getCookies();
            for (Cookie ck : cookies) {
                if (ck.getName().equals("setUser")) {
                    modelAndView.addObject("cookieValue", ck);
                    break;

                } else {
                    ck.setValue("");
                    modelAndView.addObject("cookieValue", ck);
                    break;
                }
            }
            modelAndView.addObject("message", "Login success. Welcome!");
        }else {
                user.setEmail("");
                Cookie cookie = new Cookie("setUser", setUser);
                modelAndView.addObject("cookieValue", cookie);
                modelAndView.addObject("message1", "Login failed. Try again.");
            }
        return modelAndView;

    }
}
