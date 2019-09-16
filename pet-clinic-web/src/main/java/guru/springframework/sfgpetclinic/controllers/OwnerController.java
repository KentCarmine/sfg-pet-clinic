package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String VIEWS_OWNER_CRETE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%"); // % is wildcard

        if (results.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        ModelAndView mv = new ModelAndView("owners/ownerDetails");
        mv.addObject("owner", owner);

        return mv;
    }

    @GetMapping("/new")
    public String initCreateForm(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);

        return VIEWS_OWNER_CRETE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CRETE_OR_UPDATE_FORM;
        } else {
            owner = ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model) {
        Owner owner = ownerService.findById(id);
        model.addAttribute(owner);
        return VIEWS_OWNER_CRETE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@Valid Owner owner, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CRETE_OR_UPDATE_FORM;
        } else {
            owner.setId(id);
            owner = ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
