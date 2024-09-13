package com.jiaul.websocket_chatapp_server.controlller;

import com.jiaul.websocket_chatapp_server.dto.AuthResponseDto;
import com.jiaul.websocket_chatapp_server.dto.LoginRequestDto;
import com.jiaul.websocket_chatapp_server.dto.RegistrationRequestDto;
import com.jiaul.websocket_chatapp_server.enums.AuthEnum;
import com.jiaul.websocket_chatapp_server.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AuthEnum> registerUser(@Valid @RequestBody RegistrationRequestDto requestDto) {
        return authService.registration(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logOut(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(authService.logOut(token.substring(7)));
    }

    @GetMapping("/user")
    public String getUser(){
        return "User Access";
    }
    @GetMapping("/admin")
    public String getAdmin(){
        return "Admin Access";
    }
}
