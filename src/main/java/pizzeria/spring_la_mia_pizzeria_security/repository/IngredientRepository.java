package pizzeria.spring_la_mia_pizzeria_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pizzeria.spring_la_mia_pizzeria_security.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{

}
