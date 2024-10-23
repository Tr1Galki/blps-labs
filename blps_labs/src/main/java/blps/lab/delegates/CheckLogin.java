package blps.lab.delegates;

import blps.lab.security.entity.User;
import blps.lab.security.roles.Role;
import blps.lab.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckLogin implements JavaDelegate {

    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String login = (String) delegateExecution.getVariable("login");
        String password = (String) delegateExecution.getVariable("password");

        long userId = -1;
        User user;
        try {
            user = userService.getByUsername(login);
        } catch (UsernameNotFoundException e) {
            delegateExecution.setVariable("exception", "LOGIN_IS_INCORRECT");
            throw new BpmnError("UsernameNotFoundException");
        }

        if (!user.getPassword().equals(password)) {
            delegateExecution.setVariable("exception", "PASSWORD_IS_INCORRECT");
            throw new BpmnError("IncorrectPasswordException");
        }

        if (user.getRole() != Role.ROLE_USER) {
            delegateExecution.setVariable("exception", "NOT_ALLOWED_ROLE");
            throw new BpmnError("NotAllowedRoleException");
        }
        userId = user.getId();
        delegateExecution.setVariable("userId", userId);
    }
}
