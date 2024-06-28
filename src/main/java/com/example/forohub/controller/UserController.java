package com.example.forohub.controller;

import com.example.forohub.domains.users.*;
import com.example.forohub.infra.errors.IntegrityValidation;
import com.example.forohub.infra.security.JWTTokenData;
import com.example.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping
    public ResponseEntity<Page<UserResponseData>> getUsers(Pageable page){
        return ResponseEntity.ok(userRepository.findAllBy(page).map(UserResponseData::new));
    }

   @GetMapping("/{id}")
   public ResponseEntity<Optional<UserDetails>> getUser(@PathVariable Long id, HttpServletRequest request){
        var user =getAuthenticatedUser(request);
        if (user.getId() != id ) {
            throw new IntegrityValidation("User id mismatch");
        }
        return ResponseEntity.ok(userRepository.findById(id).map(UserDetails::new));
   }

   @PutMapping
   @Transactional
   public ResponseEntity<UserResponseData> updateUser(@RequestBody @Valid UserUpdateData data, HttpServletRequest request){
        if (data.name().isEmpty() && data.password().isEmpty()){
            throw new IntegrityValidation("Name and password missing");
        }
        var user =getAuthenticatedUser(request);
        if(data.password() != null && data.name() != null){
            user.setName(data.name());
            user.setPassword(data.password());
        }
        else if (data.name() != null){
            user.setName(data.name());
        }
        else {
            user.setPassword(data.password());
        }
        return ResponseEntity.ok(new UserResponseData(user));
   }

   @DeleteMapping
   @Transactional
   public ResponseEntity<Void> deleteUser(HttpServletRequest request){
        var user =getAuthenticatedUser(request);
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
   }

    private User getAuthenticatedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        return (User) userRepository.findByMail(subject);
    }
}
