package com.ndpar.petstore.web;

import com.ndpar.petstore.dao.PetDao;
import com.ndpar.petstore.domain.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class PetController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetDao dao;

    @ResponseBody
    @RequestMapping("/pets")
    List<Pet> getPets() {
        log.info("Get all pets");
        return dao.getAllPets();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/pet", method = RequestMethod.POST)
    public void create(@RequestBody Pet pet) {
        log.info("Create: {}", pet);
        dao.create(pet);
    }

    @ResponseBody
    @RequestMapping(path = "/pet/{petId}")
    Pet getPetById(@PathVariable Long petId) {
        log.info("Get by Id: {}", petId);
        return dao.getPetById(petId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/pet", method = RequestMethod.PUT)
    public void update(@RequestBody Pet pet) {
        log.info("Update: {}", pet);
        dao.update(pet);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/pet/{petId}", method = RequestMethod.DELETE)
    void delete(@PathVariable Long petId) {
        log.info("Delete: {}", petId);
        dao.delete(petId);
    }
}