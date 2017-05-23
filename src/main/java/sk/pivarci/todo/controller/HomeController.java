package sk.pivarci.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.pivarci.todo.repo.ItemRepository;

@Controller
public class HomeController extends LoggedController {

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value = "/")
    public String home(final Model model) {
        if(currentUser() != null) {
            model.addAttribute("logged", true);
            model.addAttribute("activeItemsCount", itemRepository.findByUserIdAndDone(currentUser().getId(), false).size());
            model.addAttribute("doneItemsCount", itemRepository.findByUserIdAndDone(currentUser().getId(), true).size());
        }
        return "home";
    }

}
