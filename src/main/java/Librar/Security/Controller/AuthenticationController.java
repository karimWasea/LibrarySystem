package Librar.Security.Controller;

import Librar.Library.Modes.AuthenticationResponse;
import Librar.Library.Modes.User;
import Librar.Security.Servess.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import Librar.Security.Servess.UserMapper;
import org.springframework.web.bind.annotation.RestController;
import Librar.Library.Modes.Dtos.UserDTO;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserMapper userMapper;

    public AuthenticationController(AuthenticationService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDTO request
    ) {
        try {
            User user = userMapper.toEntity(request);
            AuthenticationResponse authResponse = authService.register(user);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            e.printStackTrace(); // Log exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserDTO request
    ) {
        User user = userMapper.toEntity(request);
        // Authenticate the user and get the authentication response
        AuthenticationResponse authResponse = authService.authenticate(user);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }
}
