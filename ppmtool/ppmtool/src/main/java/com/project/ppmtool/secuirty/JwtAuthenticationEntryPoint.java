package com.project.ppmtool.secuirty;

import com.google.gson.Gson;
import com.project.ppmtool.exception.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //here in this method we are passing the string invalid username and passsword from the Invalid login repsponse class  and converting them to json and setting various values for it
    //this class will be used in the security config class (.authenticationEntryPoint)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(jsonLoginResponse);

    }
}
