package com.bruno.api;

import com.bruno.data.auth.User;
import com.bruno.data.auth.UserRepository;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserRepository userRepository;

    @GetMapping("/api/users/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AGENT')") // customers cannot consume this resource
    public Optional<User> whoami(Principal principal) {
        return userRepository.findByEmailIgnoreCase(principal.getName());
    }
}
