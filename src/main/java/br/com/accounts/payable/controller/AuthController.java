package br.com.accounts.payable.controller;

import br.com.accounts.payable.domain.User;
import br.com.accounts.payable.dto.AuthDTO;
import br.com.accounts.payable.dto.LoginResponseDTO;
import br.com.accounts.payable.dto.RegisterUserDTO;
import br.com.accounts.payable.repository.UserRepository;
import br.com.accounts.payable.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/new")
    public ResponseEntity newUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        if(this.userRepository.findByLogin(registerUserDTO.login()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        User user = new User(registerUserDTO.login(), encryptedPassword, registerUserDTO.role());

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
