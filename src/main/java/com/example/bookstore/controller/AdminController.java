package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public final BookService bookService;
    private final OrderService orderService;

    @GetMapping
    public String adminPage(ModelMap modelMap) {
        List<Book> books = bookService.findAll();
        modelMap.addAttribute("books", books);
        return "admin/admin";
    }

    @GetMapping("/book/add")
    public String addBookPage() {
        return "admin/addBook";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin";
    }

    @GetMapping("/book/edit")
    public String editBookPage(@RequestParam("bookId") int id, ModelMap modelMap) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return "redirect:/admin";
        }
        modelMap.addAttribute("book", bookOptional.get());
        return "admin/editBook";
    }

    @PostMapping("/book/edit")
    public String editBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin";
    }

    @GetMapping("/book/remove/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.removeById(id);
        return "redirect:/admin";
    }

    @GetMapping("/allOrders")
    public String allOrdersPage(ModelMap modelMap) {
        modelMap.addAttribute("orders", orderService.findAll());
        return "admin/adminOrders";
    }
}
