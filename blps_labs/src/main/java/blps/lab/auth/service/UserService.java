package blps.lab.auth.service;

import blps.lab.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
