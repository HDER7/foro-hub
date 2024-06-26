package com.example.forohub.controller;

import com.example.forohub.domains.users.*;
import com.example.forohub.infra.security.JWTTokenData;
import com.example.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseData> signUp(@RequestBody @Valid UserData data, UriComponentsBuilder uriBuilder) {
        User user =userRepository.save(new User(data));
        UserResponseData res = new UserResponseData(user.getId(), user.getName());
        URI url = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(res);
    }


    @PostMapping
    public ResponseEntity<JWTTokenData> login(@RequestBody UserAuthenticationData user){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.mail(),user.password());
        var userAuth =authenticationManager.authenticate(authenticationToken);
        var JWTToken = tokenService.generateToken((User) userAuth.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken));
    }
}
