package com.coder.Employee.service;

import com.coder.Employee.entity.UserEntity;
import com.coder.Employee.model.User;
import com.coder.Employee.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities =
                userRepository.findAll();
        List<User> users = userEntities
                .stream()
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getEmailId()
                ))
                .collect(Collectors.toList());
        return users;
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity =
                userRepository.findById(id).get();
        User user = new User();
        BeanUtils.copyProperties(userEntity,user);
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).get();
        userRepository.delete(user);
        return true;
    }

    @Override
    public User updateUser(Long id, User user) {
        UserEntity userEntity =
                userRepository.findById(id).get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailId(user.getEmailId());

        userRepository.save(userEntity);
        return user;
    }

}
