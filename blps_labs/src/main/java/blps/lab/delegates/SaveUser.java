package blps.lab.delegates;

import blps.lab.security.entity.User;
import blps.lab.security.roles.Role;
import blps.lab.security.services.UserService;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Named
@Service
@AllArgsConstructor
public class SaveUser implements JavaDelegate {

    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String login = (String) delegateExecution.getVariable("login");
        String password = (String) delegateExecution.getVariable("password");
        String email = (String) delegateExecution.getVariable("email");
        User user = User.builder()
                .username(login)
                .password(password)
                .email(email)
                .role(Role.ROLE_USER)
                .build();
        userService.create(user);
    }
}
