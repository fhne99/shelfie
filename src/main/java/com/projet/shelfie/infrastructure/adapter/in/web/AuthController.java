package com.projet.shelfie.infrastructure.adapter.in.web;

import com.projet.shelfie.domain.port.in.LoginUseCase;
import com.projet.shelfie.domain.port.in.LogoutUseCase;
import com.projet.shelfie.domain.port.in.RegisterUseCase;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.request.LoginRequest;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.request.RegisterRequest;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.response.LoginResponse;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        var user = registerUseCase.register(
                new RegisterUseCase.RegisterCommand(
                        request.email(),
                        request.pseudo(),
                        request.password()
                )
        );
        return new RegisterResponse(user.id(), user.email(), user.pseudo());
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        var result = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.email(), request.password())
        );
        return new LoginResponse(result.accessToken(), result.expiresIn());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String authHeader) {
        logoutUseCase.logout(
                new LogoutUseCase.LogoutCommand(authHeader.substring(7))
        );
    }
}