package com.example.BackendShop.security;
import com.example.BackendShop.exception.InvalidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import org.springframework.web.method.HandlerMethod;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Interceptor pentru accesarea adnotării personalizate și a header-ului cererii.
 */
@Component
public class ProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // Check if method has protected annotation
            if (method.isAnnotationPresent(Protected.class)) {
                Protected annotation = method.getAnnotation(Protected.class);
                String[] annotationValue = annotation.value();

                String authorizationHeader = request.getHeader("Authorization");
                if (authorizationHeader != null) {
                   String  token = authorizationHeader.substring(7);
                    jwtService.validateJwtToken(token);
                    if (!Arrays.asList(annotationValue).contains(jwtService.getRole(token))) {
                        throw new InvalidToken("You are not allowed to access this resource");
                    }

                } else {
                    //there is no token
                    return false;
                }
            }else{
                return true;
            }
        }

        return true;
    }


}


