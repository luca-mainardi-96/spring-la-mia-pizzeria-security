package pizzeria.spring_la_mia_pizzeria_security.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import pizzeria.spring_la_mia_pizzeria_security.model.Ingredient;
import pizzeria.spring_la_mia_pizzeria_security.model.Pizza;
import pizzeria.spring_la_mia_pizzeria_security.model.SpecialOffer;
import pizzeria.spring_la_mia_pizzeria_security.repository.IngredientRepository;
import pizzeria.spring_la_mia_pizzeria_security.repository.PizzaRepository;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;
    
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("")
    public String filter(@RequestParam(name="keyword", required=false) String keyword, Model model){
        List<Pizza> result;
        if(keyword == null || keyword.isBlank()){
            result = repository.findAll();
        } else{
            result = repository.findByNameContainingIgnoreCase(keyword);
        }
        
        model.addAttribute("list", result);
        
        return "pizza/index";
    }

    @GetMapping("/details/{id}")
    public String detail(@PathVariable(name="id") Integer id, Model model){
        Optional<Pizza> optionalPizza = repository.findById(id);
        if(optionalPizza.isPresent()){
            model.addAttribute("pizza", optionalPizza.get());
            model.addAttribute("empty", false);
        } else {
            model.addAttribute("empty", true);
        }

        return "pizza/details";
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientsList", ingredientRepository.findAll());
        return "pizza/insert";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("pizza") Pizza formPizza, 
                        BindingResult bindingResult, 
                        Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("ingredientsList", ingredientRepository.findAll());
            return "pizza/insert";
        }

        repository.save(formPizza);
        return "redirect:/pizza";
    }    

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        Pizza pizza = repository.findById(id).get();
        for(Ingredient ing : pizza.getIngredients()){
            ing.getPizzas().remove(pizza);
        }
        repository.deleteById(id);
        return "redirect:/pizza";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("ingredientsList", ingredientRepository.findAll());
        return "pizza/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza,
                        BindingResult bindingResult, Model model){

    Pizza oldPizza = repository.findById(formPizza.getId()).get();

    if(!oldPizza.getName().equals(formPizza.getName())){
        bindingResult.addError(new FieldError("pizza", "name", "Field name in unmodifiable."));
    }
                            
    if(bindingResult.hasErrors()){
        model.addAttribute("ingredientsList", ingredientRepository.findAll());
        return "pizza/edit";
    }

    repository.save(formPizza);
    return "redirect:/pizza";
    }

    @GetMapping("/details/{id}/offer")
    public String offer(@PathVariable("id") Integer id, Model model){
        SpecialOffer offer = new SpecialOffer();
        offer.setPizza(repository.findById(id).get());
        model.addAttribute("offer", offer);
        model.addAttribute("editMode", false);

        return "/special_offer/edit";
        
    }

}
