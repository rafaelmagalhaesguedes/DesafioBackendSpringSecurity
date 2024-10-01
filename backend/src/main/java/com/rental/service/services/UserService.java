package com.rental.service.services;

import com.rental.service.entities.User;
import com.rental.service.enums.Role;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        return userRepository.save(user);
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private static String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
