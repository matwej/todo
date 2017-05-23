package sk.pivarci.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.pivarci.todo.model.Item;
import sk.pivarci.todo.repo.ItemRepository;

import javax.validation.Valid;

@RequestMapping(value = "/items")
@Controller
public class ItemsController extends LoggedController {

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("items", itemRepository.findByUserId(currentUser().getId()));
        model.addAttribute("item", new Item());
        return "items/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Item item,
                         BindingResult br,
                         final Model model) {
        if (br.hasErrors()) {
            model.addAttribute("items", itemRepository.findByUserId(currentUser().getId()));
            model.addAttribute("item", item);
            return "items/index";
        }

        item.setUser(currentUser());
        itemRepository.save(item);
        return "redirect:/items";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") long id,
                       final Model model,
                       final RedirectAttributes redirectAttributes) {
        Item item = itemRepository.findOne(id);
        if (item == null) return "redirect:/items";
        if (item.getUser().getId() != currentUser().getId()) {
            redirectAttributes.addFlashAttribute("msg", "Operation not permitted");
            redirectAttributes.addFlashAttribute("msgType", "danger");
            return "redirect:/items";
        }

        model.addAttribute("item", item);
        return "items/edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Item item,
                         BindingResult br,
                         final Model model,
                         final RedirectAttributes redirectAttributes) {
        if (item.getUser() == null && item.getUser().getId() != currentUser().getId()) {
            redirectAttributes.addFlashAttribute("msg", "Operation not permitted");
            redirectAttributes.addFlashAttribute("msgType", "danger");
            return "redirect:/items";
        }
        if (br.hasErrors()) {
            model.addAttribute("item", item);
            return "items/edit";
        }

        itemRepository.save(item);
        redirectAttributes.addFlashAttribute("msg", "TODO item successfully updated");
        redirectAttributes.addFlashAttribute("msgType", "success");
        return "redirect:/items";
    }

    @RequestMapping(value = "/{id}/done", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Item switchDone(@PathVariable("id") long id) {
        Item item = itemRepository.findOne(id);
        if (item != null && item.getUser().getId() == currentUser().getId()) {
            item.setDone(!item.isDone());
            itemRepository.save(item);
            System.out.println(item.getId());
            return item;
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {
        try {
            itemRepository.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Item deleted");
            redirectAttributes.addFlashAttribute("msgType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Item could not be deleted");
            redirectAttributes.addFlashAttribute("msgType", "danger");
        }
        return "redirect:/items";
    }
}
