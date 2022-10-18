package com.example.bookstore.controller;

import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.security.CurrentUser;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) throws MessagingException {
        try {
            userService.save(user);
        } catch (DuplicateResourceException e) {
            e.getMessage();
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("email") String email,
                             @RequestParam("token") String token) throws Exception {
        userService.verifyUser(email, token);
        return "redirect:/";
    }

    @GetMapping("/myOrders")
    public String myOrdersPage(@AuthenticationPrincipal CurrentUser currentUser,
                               ModelMap modelMap) {
        List<Order> myOrders = orderService.findAllByUser(currentUser.getUser());
        modelMap.addAttribute("myOrders", myOrders);
        return "myOrders";
    }
}
