package corse_work.demo.service.impl;

import corse_work.demo.model.User;
import corse_work.demo.model.repository.UserRepository;
import corse_work.demo.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.UserService;

import javax.validation.constraints.Null;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

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
    public Optional<User> signIn(String email, String pass) {

        Optional<User> user = userRepository.findByEmail(email);
        if ( user.isPresent()){
            if ( passwordService.passwordMatched(pass, user.get().getPassword()) ) {
                return user;
            }
        }
        return Optional.empty();
    }
}
