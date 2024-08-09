package Librar.Security.Servess;

import Librar.Library.Modes.AuthenticationResponse;
import Librar.Library.Modes.Role;
import Librar.Library.Modes.Token;
import Librar.Library.Modes.User;
import Librar.Security.repository.RoleRepository;
import Librar.Security.repository.TokenRepository;
import Librar.Security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager,
                                 RoleService roleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    public AuthenticationResponse register(User request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null, "User already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Handle roles
        Set<Role> roles = request.getRoles(); // Assume roles are provided in request

        if (roles == null || roles.isEmpty()) {
            // Create an "admin" role if no roles are provided
            Role adminRole = roleService.findRoleByName("admin")
                    .orElseGet(() -> roleService.createRole( "admin"));
            roles = Set.of(adminRole);
        } else {
            roles = roles.stream()
                    .map(role -> roleService.findRoleByName(role.getName()).orElseGet(() -> roleService.createRole(role.getName())))
                    .collect(Collectors.toSet());
        }

        user.setRoles(roles);

        user = repository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User registration was successful");
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokensByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(validTokens);
        }
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No user found"));

        if (jwtService.isValidRefreshToken(token, user)) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokensByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken, "New token generated"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
