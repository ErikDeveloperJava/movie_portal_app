package net.movies.interceptor;

import net.movies.config.security.UserDetailsImpl;
import net.movies.model.User;
import net.movies.model.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl)principal;
            request.setAttribute("user",userDetails.getUser());
            LOGGER.debug("user : {} {} added to request attribute",userDetails.getUser(),"\n");
        }else {
            User user = User
                    .builder()
                    .role(UserRole.ANONYMOUS)
                    .build();
            request.setAttribute("user",user);
            LOGGER.debug("user : {} {} added to request attribute",user,"\n");
        }
        return true;
    }
}
