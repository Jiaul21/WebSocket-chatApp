package com.jiaul.websocket_chatapp_server.service;

import com.jiaul.websocket_chatapp_server.entity.UserCredential;
import com.jiaul.websocket_chatapp_server.repository.UserCredentialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("+++++++++++++++++++++++++ loadUserByUsername ++++++++++++++++++++");
        System.out.println("user name: "+username);
        UserDetails userDetails= userCredentialRepository.findByUserName(username).orElseThrow();
        return userDetails;
    }

    public UserCredential saveUserCredential(UserCredential userCredential){
        return userCredentialRepository.save(userCredential);
    }

    public boolean hasUserCredentialByUserName(String userName){
        return userCredentialRepository.existsByUserName(userName);
    }
}
