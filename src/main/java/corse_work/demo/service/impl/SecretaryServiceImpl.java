package corse_work.demo.service.impl;

import corse_work.demo.model.Secretary;
import corse_work.demo.model.Student;
import corse_work.demo.model.User;
import corse_work.demo.model.repository.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import corse_work.demo.service.interfaces.SecretaryService;

import java.util.Optional;


@Service
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    private SecretaryRepository secretaryRepository;


    @Override
    public Secretary create(Secretary secretary) {
        return secretaryRepository.saveAndFlush(secretary);
    }

    @Override
    public Secretary update(Secretary secretary) {
        return secretaryRepository.saveAndFlush(secretary);
    }

    @Override
    public Optional<Secretary> getById(Long id) {
        return secretaryRepository.findById(id);
    }

    @Override
    public Optional<Secretary> getSecretaryByUser(User u) {
        return secretaryRepository.getSecretaryByUser(u);
    }

    @Override
    public void delete(Long id) {
        secretaryRepository.deleteById(id);
    }
}
