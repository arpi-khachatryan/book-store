package com.example.bookstore.repository;

import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUser(User user);
}
