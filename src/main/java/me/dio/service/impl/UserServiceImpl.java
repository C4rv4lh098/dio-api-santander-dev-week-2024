package me.dio.service.impl;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())){
            throw new IllegalArgumentException("This Account number already exists.");
        }
        return this.userRepository.save(userToCreate);
    }

    @Override
    public User update(Long id, User userToUpdate) {
        User userUpdated = this.findById(id);
        if (!userUpdated.getId().equals(userToUpdate.getId())) {
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        userUpdated.setName(userToUpdate.getName());
        userUpdated.setAccount(userToUpdate.getAccount());
        userUpdated.setCard(userToUpdate.getCard());
        userUpdated.setFeatures(userToUpdate.getFeatures());
        userUpdated.setNews(userToUpdate.getNews());

        return this.userRepository.save(userUpdated);
    }


    @Override
    public void delete(Long id) {
        User userToDeleted = this.findById(id);
        this.userRepository.delete(userToDeleted);
    }
}
