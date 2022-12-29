package com.project.ppmtool.web;

import com.project.ppmtool.domain.User;
import com.project.ppmtool.payload.JWTLoginSuccessResponse;
import com.project.ppmtool.payload.LoginRequest;
import com.project.ppmtool.secuirty.JwtTokenProvider;
import com.project.ppmtool.services.UserService;
import com.project.ppmtool.services.ValidationErrorService;
import com.project.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.project.ppmtool.secuirty.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping(value ="/api/users")
public class UserController {
    @Autowired
    private ValidationErrorService mapValidationErrorService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result)
    {
        //validate password match
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap !=null)return errorMap;
        User userCreated  = userService.saveUser(user);
        return new ResponseEntity<User>(userCreated, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest , BindingResult result)
    {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null )return errorMap;
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX+ jwtTokenProvider.generatesToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,jwt));

    }
}
