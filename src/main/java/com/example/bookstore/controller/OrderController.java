package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.security.CurrentUser;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("/order/new")
    public String newOrder(@RequestParam("bookId") int bookId,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Book> byId = bookService.findById(bookId);
        if (byId.isEmpty()) {
            return "redirect:/";
        }
        Order order = Order.builder()
                .book(byId.get())
                .user(currentUser.getUser())
                .orderDateTime(new Date())
                .amount(byId.get().getPrice())
                .build();
        orderService.save(order);
        return "redirect:/myOrders";
    }
}
