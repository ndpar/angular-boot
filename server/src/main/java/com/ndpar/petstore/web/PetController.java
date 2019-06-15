package com.ndpar.petstore.web;

import com.ndpar.petstore.dao.PetDao;
import com.ndpar.petstore.domain.Pet;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * http://localhost:8080/swagger-ui.html
 *
 * API: http://petstore.swagger.io/#/pet
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Validated
public class PetController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetDao dao;

    @ApiOperation(value = "Find all pets (by name)", nickname = "getPets", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return a list of pets", response = Pet.class, responseContainer = "List")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "name", value = "Pet's name (substring)",
                    dataType = "string", paramType = "query", defaultValue = "n")
    })
    @Valid
    @GetMapping("/pet")
    List<Pet> getPets(@RequestParam(name = "name", required = false) String name) {
        log.info("Get pets. Name: {}", name);
        return name != null ? dao.getPetsByName(name) : dao.getAllPets();
    }

    @ApiOperation(value = "Add a new pet to the store", nickname = "createPet")
    @Valid
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pet")
    Pet create(@RequestBody Pet pet) {
        log.info("Create: {}", pet);
        return dao.create(pet);
    }

    @ApiOperation(value = "Find pet by ID", nickname = "getPetById", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "petId", value = "Pet's id",
                    dataType = "long", paramType = "path", defaultValue = "11")
    })
    @Valid
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

    @ApiOperation(value = "Update pet in the store", nickname = "updatePet")
    @PutMapping("/pet")
    void update(@Valid @RequestBody Pet pet, HttpServletResponse response) {
        log.info("Update: {}", pet);
        if (dao.update(pet) != 1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @ApiOperation(value = "Delete a pet", nickname = "deletePet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "petId", value = "Pet's id", dataType = "long", paramType = "path")
    })
    @DeleteMapping("/pet/{petId}")
    void delete(@PathVariable Long petId, HttpServletResponse response) {
        log.info("Delete: {}", petId);
        if (dao.delete(petId) != 1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}