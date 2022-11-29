package com.switchfully.eurder.security;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.exceptions.security.UnauthorizedException;
import com.switchfully.eurder.exceptions.user.UserNotFoundException;
import com.switchfully.eurder.exceptions.security.WrongPasswordException;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateAuthorization(String authorization, Role securityRole) {
        EmailPassword usernamePassword = getUsernamePassword(authorization);
        User user = userRepository.findByEmailAddress(usernamePassword.email()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.doesPasswordMatch(usernamePassword.password())) {
            throw new WrongPasswordException("Wrong password");
        }
        if (!user.getRole().equals(securityRole)) {
            throw new UnauthorizedException("You are not authorized to access this information");
        }
        return user;
    }

    private EmailPassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new EmailPassword(username, password);
    }
}
