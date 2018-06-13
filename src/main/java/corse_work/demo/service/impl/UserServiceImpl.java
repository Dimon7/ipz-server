package corse_work.demo.service.impl;

import corse_work.demo.security.JwtTokenProvider;
import corse_work.demo.controllers.Exceptions.CustomException;
import corse_work.demo.model.User;
import corse_work.demo.model.repository.UserRepository;
import corse_work.demo.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.UserService;


import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean exists(String email) {
        Optional<User> user = userRepository.findByEmail( email);

        return user.isPresent();
    }

    @Override
    public String signIn(String name, String pass) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, pass));
            return jwtTokenProvider.createToken(name, userRepository.findByName(name).get().getRole() );
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }



}
