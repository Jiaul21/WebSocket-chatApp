package com.jiaul.websocket_chatapp_server.service;

import com.jiaul.websocket_chatapp_server.dto.AuthResponseDto;
import com.jiaul.websocket_chatapp_server.dto.LoginRequestDto;
import com.jiaul.websocket_chatapp_server.dto.RegistrationRequestDto;
import com.jiaul.websocket_chatapp_server.entity.Profile;
import com.jiaul.websocket_chatapp_server.entity.Token;
import com.jiaul.websocket_chatapp_server.entity.UserCredential;
import com.jiaul.websocket_chatapp_server.entity.WebChannel;
import com.jiaul.websocket_chatapp_server.enums.AuthEnum;
import com.jiaul.websocket_chatapp_server.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MapperService mapperService;
    @Autowired
    private UserCredentialService userCredentialService;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<AuthEnum> registration(RegistrationRequestDto registrationRequestDto) {
        if (userCredentialService.hasUserCredentialByUserName(registrationRequestDto.getUserName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthEnum.USERNAME_ALREADY_TAKEN);

        UserCredential userCredential = mapperService.createUserCredential_By_RegistrationRequestDto(registrationRequestDto);
        Profile profile = mapperService.createProfile_By_RegistrationRequestDto(registrationRequestDto);
        profile.setUserCredential(userCredential);
        userCredential.setProfile(profile);
        WebChannel webChannel= mapperService.createWebChannel_By_UserCredential(userCredential);
        profile.setWebChannels(Set.of(webChannel));
        try {
            UserCredential savedUserCredential = userCredentialService.saveUserCredential(userCredential);
            if (savedUserCredential != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(AuthEnum.REGISTRATION_SUCCESS);
            }
        } catch (Exception e) {
            System.out.println(AuthEnum.INTERNAL_SERVER_ERROR + ":------ " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AuthEnum.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<AuthResponseDto> login(LoginRequestDto loginRequestDto) {

        try {
            UserCredential userCredential = (UserCredential) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword())).getPrincipal();

            Token token = mapperService.createJwtToken_By_UserCredential(userCredential);
            Set<Token> tokenSet = userCredential.getTokens();
            tokenSet.add(token);
            userCredential.setTokens(tokenSet);

            Token savedjwtToken = tokenService.saveJwtToken(token);
            System.out.println(token);
            if (savedjwtToken != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(mapperService.createAuthResponse(token));
            }
        } catch (Exception e) {
            System.out.println(AuthEnum.INTERNAL_SERVER_ERROR + ":------ " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponseDto.builder().responseMessage(AuthEnum.LOGIN_FAILED).build());
    }

    public String logOut(String jwtToken) {
        return tokenService.deleteTokenByJwtToken(jwtToken);
    }
}
