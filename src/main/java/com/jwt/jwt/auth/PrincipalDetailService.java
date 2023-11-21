package com.jwt.jwt.auth;

import com.jwt.jwt.model.User_entity;

import com.jwt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

// http://localhost:8080/login => 돟작을 안한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
System.out.println("PrincipalDetailService의 loadUserByUsername()");
        User_entity userEntity = userRepository.findByUsername(username);
        return new PrincipalDetails(userEntity);
    }
}
