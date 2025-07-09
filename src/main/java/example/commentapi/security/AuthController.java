package example.commentapi.security;

import example.commentapi.model.User;
import example.commentapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        return userRepository.findByUsername(login.getUsername())
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPasswordHash()))
                .map(user -> {
                    var token = jwtService.generateToken(user.getUsername(), java.util.List.of(user.getRole()));
                    return ResponseEntity.ok(java.util.Collections.singletonMap("token", token));
                })
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Benutzername existiert bereits");
        }

        String hash = passwordEncoder.encode(request.getPassword());
        User admin = new User(request.getUsername(), hash, "ADMIN");
        userRepository.save(admin);

        return ResponseEntity.ok("Admin erfolgreich erstellt");
    }
}
