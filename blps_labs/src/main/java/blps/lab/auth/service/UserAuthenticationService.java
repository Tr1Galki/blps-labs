package blps.lab.auth.service;

import blps.lab.auth.entity.User;
import blps.lab.auth.exceptions.UserNotFoundException;
import blps.lab.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final UserRepository userRepository;

    /**
     * Получение текущего пользователя при авторизации
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
