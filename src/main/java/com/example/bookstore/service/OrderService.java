package com.example.bookstore.service;

import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}
