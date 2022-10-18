package com.example.bookstore.service;

import com.example.bookstore.entity.User;
import com.example.bookstore.entity.UserRole;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) throws DuplicateResourceException, MessagingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER);

        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        userRepository.save(user);
        mailService.sendHTMLEmail(user.getEmail(), "Please, verify your email",
                "Hi " + user.getName() + "\n" +
                        "please, verify your account by clicking on this link <a href=\"http://localhost:8080/user/verify?email=" +
                        user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
    }

    public void verifyUser(String email, String token) throws Exception {
        Optional<User> userOptional = userRepository.findByEmailAndVerifyToken(email, token);
        if (userOptional.isEmpty()) {
            throw new Exception("User does not exists with email and token");
        }
        User user = userOptional.get();
        if (user.isEnabled()) {
            throw new Exception("user already enabled");
        }
        user.setEnabled(true);
        user.setVerifyToken(null);
        userRepository.save(user);
    }
}
