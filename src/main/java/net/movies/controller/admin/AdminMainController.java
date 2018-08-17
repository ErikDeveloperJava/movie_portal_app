package net.movies.controller.admin;

import net.movies.page.Pages;
import net.movies.service.AdminPageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AdminPageService pageService;

    @GetMapping
    public String main(Model model, Pageable pageable){
        model.addAttribute("page",pageService.mainPage(pageable));
        model.addAttribute("pages",pageable.getPageNumber());
        LOGGER.debug("redirect to '{}' page,model : {}",ADMIN_INDEX_PAGE,model);
        return ADMIN_INDEX_PAGE;
    }
}
