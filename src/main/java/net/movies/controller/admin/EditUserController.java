package net.movies.controller.admin;

import net.movies.model.User;
import net.movies.service.UserService;
import net.movies.util.ControllerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class EditUserController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String strId,
                         @RequestAttribute("user")User user) {
        LOGGER.debug("userId : {}",strId);
        int userId = controllerUtil.parseToInteger(strId);
        if(userId == -1 || user.getId() == userId || !userService.existsById(userId)){
            LOGGER.debug("invalid userId , redirect to '/admin' url");
        }else {
            userService.deleteById(userId);
            LOGGER.debug("redirect to '/admin' url");
        }
        return "redirect:/admin";
    }
}
