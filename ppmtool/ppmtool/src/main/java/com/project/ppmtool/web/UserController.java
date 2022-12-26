package com.project.ppmtool.web;

import com.project.ppmtool.domain.User;
import com.project.ppmtool.services.UserService;
import com.project.ppmtool.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value ="/api/users")
public class UserController {
    @Autowired
    private ValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result)
    {
        //validate password match
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap !=null)return errorMap;
        User userCreated  = userService.saveUser(user);
        return new ResponseEntity<User>(userCreated, HttpStatus.CREATED);

    }
}
