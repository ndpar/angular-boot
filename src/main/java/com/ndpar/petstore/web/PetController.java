package com.ndpar.petstore.web;

import com.ndpar.petstore.domain.Pet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class PetController {

    @ResponseBody
    @RequestMapping("/pet/{petId}")
    Pet getPetById(@PathVariable Long petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Doggie");
        return pet;
    }
}