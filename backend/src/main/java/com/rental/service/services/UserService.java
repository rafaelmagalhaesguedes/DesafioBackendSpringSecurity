package com.rental.service.services;

import com.rental.service.entities.User;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) throws ExistingUserException {
        var existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new ExistingUserException();
        }

        // Encode password
        var encodedPassword = passwordEncoder(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User update(Long userId, User user) throws UserNotFoundException {
        var userFromDb = findById(userId);

        userFromDb.setName(user.getName());
        userFromDb.setEmail(user.getEmail());

        userRepository.save(userFromDb);

        return userFromDb;
    }

    public User delete(Long userId) throws UserNotFoundException {
        var user = findById(userId);

        userRepository.deleteById(user.getId());

        return user;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    private static String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
