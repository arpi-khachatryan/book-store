package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import com.example.bookstore.entity.UserRole;
import com.example.bookstore.security.CurrentUser;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;


    @GetMapping("/")
    public String mainPage(ModelMap modelMap) {
        List<Book> last20Books = bookService.findLast20Books();
        modelMap.addAttribute("books", last20Books);
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getUserRole() == UserRole.USER) {
                return "redirect:/";
            } else if (user.getUserRole() == UserRole.ADMIN) {
                return "redirect:/admin";
            }
        }
        return "redirect:/";
    }
}
