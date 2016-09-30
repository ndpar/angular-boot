package com.ndpar.petstore.dao;

import com.ndpar.petstore.domain.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetDao {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private RowMapper<Pet> mapper = new BeanPropertyRowMapper<>(Pet.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Pet pet) {
        log.debug("Create: {}", pet);
        jdbcTemplate.update("insert into pets (name) values (?)", pet.getName());
    }

    public List<Pet> getAllPets() {
        log.debug("Get all pets");
        return jdbcTemplate.query("select * from pets", mapper);
    }

    public Pet getPetById(Long id) {
        log.debug("Get by Id: {}", id);
        return jdbcTemplate.queryForObject("select * from pets where id=?", mapper, id);
    }

    public void update(Pet pet) {
        log.debug("Update: {}", pet);
        jdbcTemplate.update("update pets set name=? where id=?", pet.getName(), pet.getId());
    }

    public int delete(Long id) {
        log.debug("Delete: {}", id);
        return jdbcTemplate.update("delete from pets where id=?", id);
    }
}
