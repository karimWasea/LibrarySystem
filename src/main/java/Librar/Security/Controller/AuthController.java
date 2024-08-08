//package Librar.Security.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import Librar.Security.UtilTiz.JwtUtil;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//
//            if (authentication.isAuthenticated()) {
//                String token = jwtUtil.generateToken(username);
//                return ResponseEntity.ok().body(new JwtResponse(token));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            }
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//
//    public static class JwtResponse {
//        private String token;
//
//        public JwtResponse(String token) {
//            this.token = token;
//        }
//
//        public String getToken() {
//            return token;
//        }
//    }
//}
