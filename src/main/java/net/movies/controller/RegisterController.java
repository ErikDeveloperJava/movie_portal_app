package net.movies.controller;

import net.movies.model.User;
import net.movies.page.Pages;
import net.movies.service.UserService;
import net.movies.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegisterController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping
    public String registerGet(@RequestAttribute("user")User user, Model model){
        model.addAttribute("user",user);
        LOGGER.debug("redirect to '{}' page , model : {}",REGISTER,model);
        return REGISTER;
    }

    @PostMapping
    public String registerPost(@Valid User user, BindingResult result,
                               @RequestParam("_image")MultipartFile image,
                               @RequestAttribute("user") User u,
                               Model model){
        LOGGER.debug("parameters ( {},error counts : {} {} )",user,result.getFieldErrorCount(),image.getOriginalFilename());
        if(result.hasErrors()){
            LOGGER.debug("redirect to '{}' page",REGISTER);
            return REGISTER;
        }
        if(userService.existsByUsername(user.getUsername())){
            result.addError(new FieldError("user","username","user with username already exists"));
            LOGGER.debug("user with username already exists,redirect to '{}' page",REGISTER);
            return REGISTER;
        }
        if(image.isEmpty() || !imageUtil.checkImageFormat(image.getContentType())){
            result.addError(new FieldError("user","image","you entered invalid image file"));
            LOGGER.debug("invalid image,redirect to '{}' page",REGISTER);
            return REGISTER;
        }
        userService.register(user,image);
        LOGGER.debug("redirect to '/login' url");
        return "redirect:/login";
    }
}
