package com.ndpar.petstore.web;

import com.ndpar.petstore.dao.PetDao;
import com.ndpar.petstore.domain.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * API: http://petstore.swagger.io/#/pet
 */
@RestController
@RequestMapping("/app")
public class PetController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetDao dao;

    @GetMapping("/pet")
    List<Pet> getPets(@RequestParam(name = "name", required = false) String name) {
        log.info("Get pets. Name: {}", name);
        return name != null ? dao.getPetsByName(name) : dao.getAllPets();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pet")
    void create(@RequestBody Pet pet) {
        log.info("Create: {}", pet);
        dao.create(pet);
    }

    @GetMapping("/pet/{petId}")
    Pet getPetById(@PathVariable Long petId, HttpServletResponse response) {
        log.info("Get by Id: {}", petId);
        Pet pet = dao.getPetById(petId);
        log.trace("Pet: {}", pet);
        if (pet == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return pet;
    }

    @PutMapping("/pet")
    void update(@RequestBody Pet pet, HttpServletResponse response) {
        log.info("Update: {}", pet);
        if (dao.update(pet) != 1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @DeleteMapping("/pet/{petId}")
    void delete(@PathVariable Long petId, HttpServletResponse response) {
        log.info("Delete: {}", petId);
        if (dao.delete(petId) != 1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}