package com.project.ppmtool.services;

import com.project.ppmtool.domain.User;
import com.project.ppmtool.exception.UserNameAlreadyExistsException;
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
        /*further tasks
          1- username has  to be unique exception
          2- make sure the passsword and ocnfirm password match
          3- we don't persist or show thw confirm password

         */

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //username has  to be unique exception
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }
        catch (Exception e){
            throw new UserNameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }



    }
}
