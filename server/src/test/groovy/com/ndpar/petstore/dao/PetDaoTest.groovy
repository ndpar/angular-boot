package com.ndpar.petstore.dao

import com.ndpar.petstore.Application
import com.ndpar.petstore.domain.Pet
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
class PetDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate

    @Autowired
    private PetDao dao

    @Test
    void create() {
        assert dao.create(new Pet(name: 'Test')) == new Pet(id: 21, name: 'Test')
    }

    @Test
    void getAllPets() {
        assert !dao.getAllPets().empty
    }

    @Test
    void getAllPets_empty() {
        jdbcTemplate.update('delete from pets')
        assert dao.getAllPets().empty
    }

    @Test
    void getPetsByName() {
        assert dao.getPetsByName('ma') == [
                new Pet(id: 15, name: 'Magneta'),
                new Pet(id: 16, name: 'RubberMan'),
                new Pet(id: 17, name: 'Dynama'),
                new Pet(id: 19, name: 'Magma')
        ]
    }

    @Test
    void getPetById() {
        assert dao.getPetById(11) == new Pet(id: 11, name: 'Mr. Nice')
    }

    @Test
    void getPetById_not_exist() {
        assert dao.getPetById(1) == null
    }

    @Test
    void update() {
        assert dao.update(new Pet(id: 11, name: 'Ladybug')) == 1
        assert jdbcTemplate.queryForMap('select name from pets where id=?', 11).name == 'Ladybug'
    }

    @Test
    void update_not_exist() {
        assert dao.update(new Pet(id: 1, name: 'Ladybug')) == 0
    }

    @Test
    void delete() {
        assert dao.delete(11) == 1
        assert jdbcTemplate.queryForList('select * from pets where id=?', 11).isEmpty()
    }

    @Test
    void delete_not_exist() {
        assert dao.delete(1) == 0
    }
}
