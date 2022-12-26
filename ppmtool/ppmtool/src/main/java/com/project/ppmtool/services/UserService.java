package com.project.ppmtool.services;

import com.project.ppmtool.domain.User;
import com.project.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser)
    {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        /*further tasks
          1- username has  to be unique exception
          2- make sure the passsword and ocnfirm password match
          3- we don't persist or show thw confirm password

         */
        return userRepository.save(newUser);
    }
}
