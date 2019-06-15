package com.ndpar.petstore.web

import com.ndpar.petstore.dao.PetDao
import com.ndpar.petstore.domain.Pet
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.NestedServletException

import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.request.RequestDocumentation.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
class PetControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets")

    @Autowired
    WebApplicationContext context

    @MockBean
    PetDao dao

    MockMvc mvc

    @Before
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build()
    }

    static path(String endPoint) {
        '/api' + endPoint
    }

    @Test
    void get_pet_200() {
        given(dao.getAllPets()).willReturn([
                new Pet(id: 1, name: 'Premier'),
                new Pet(id: 2, name: 'Seconde')
        ])

        mvc.perform(get(path('/pet')).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string('[{"id":1,"name":"Premier"},{"id":2,"name":"Seconde"}]'))
    }

    @Test
    void get_pet_param_200() {
        given(dao.getPetsByName('e')).willReturn([
                new Pet(id: 1, name: 'Premier'),
                new Pet(id: 2, name: 'Seconde')
        ])

        mvc.perform(get(path('/pet/?name=e')).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string('[{"id":1,"name":"Premier"},{"id":2,"name":"Seconde"}]'))
                .andDo(document('get-pet', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName('name').optional().description('Part of the name')
                ),
                responseFields([
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Pet's ID"),
                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("Pet's name")
                ])))
    }

    @Test
    void post_pet_201() {
        given(dao.create(new Pet(name: 'Test'))).willReturn(new Pet(id: 1, name: 'Test'))

        mvc.perform(post(path('/pet')).contentType(APPLICATION_JSON).content('{"name":"Test"}'))
                .andExpect(status().isCreated())
                .andExpect(content().string('{"id":1,"name":"Test"}'))
                .andDo(document('post-pet', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("Pet's name")
                ),
                responseFields([
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Pet's ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("Pet's name")
                ])))
    }

    @Test
    void post_pet_400() {
        mvc.perform(post(path('/pet')).contentType(APPLICATION_JSON).content('{"name":Test}'))
                .andExpect(status().isBadRequest()) // Spec says 405 (Method not allowed), why?
        verify(dao, times(0)).create(new Pet(name: 'Test'))
    }

    @Test
    void get_pet_1_200() {
        given(dao.getPetById(1)).willReturn(new Pet(id: 1, name: 'Test'))

        mvc.perform(get(path('/pet/{id}'), 1).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string('{"id":1,"name":"Test"}'))
                .andDo(document('get-pet-by-id', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName('id').description("Pet's ID")
                ),
                responseFields([
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Pet's ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("Pet's name")
                ])))
    }

    @Test
    void get_pet_1_400() {
        mvc.perform(get(path('/pet/ABC')))
                .andExpect(status().isBadRequest())
    }

    @Test
    void get_pet_1_404() {
        given(dao.getPetById(1)).willReturn(null)

        mvc.perform(get(path('/pet/1')))
                .andExpect(status().isBadRequest())
    }

    @Test
    void put_pet_200() {
        given(dao.update(new Pet(id: 1, name: 'Test'))).willReturn(1)

        mvc.perform(put(path('/pet')).contentType(APPLICATION_JSON).content('{"id":1,"name":"Test"}'))
                .andExpect(status().isOk())
                .andDo(document('put-pet', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Pet's ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("Pet's name")
                )))
    }

    @Test
    void put_pet_400() {
        mvc.perform(put(path('/pet')).contentType(APPLICATION_JSON).content('{"id":ABC,"name":"Test"}'))
                .andExpect(status().isBadRequest())
    }

    @Test
    void put_pet_404() {
        given(dao.update(new Pet(id: 1, name: 'Test'))).willReturn(0)

        mvc.perform(put(path('/pet')).contentType(APPLICATION_JSON).content('{"id":1,"name":"Test"}'))
                .andExpect(status().isNotFound())
    }

    @Test
    void delete_pet_1_200() {
        given(dao.delete(1)).willReturn(1)
        mvc.perform(delete(path('/pet/{id}'), 1))
                .andExpect(status().isOk())
                .andDo(document('delete-pet', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName('id').description("Pet's ID")
                )))
    }

    @Test
    void delete_pet_1_400() {
        mvc.perform(delete(path('/pet/ABC'))).andExpect(status().isBadRequest())
    }

    @Test
    void delete_pet_1_404() {
        given(dao.delete(1)).willReturn(0)
        mvc.perform(delete(path('/pet/1'))).andExpect(status().isNotFound())
    }

    // ------------------------------------------------------------------------
    // Validation Tests
    // ------------------------------------------------------------------------

    @Test(expected = NestedServletException.class)
    void get_pet_null_id() {
        given(dao.getAllPets()).willReturn([new Pet(name: 'Premier')])
        mvc.perform(get(path('/pet')).accept(APPLICATION_JSON))
    }

    @Test(expected = NestedServletException.class)
    void get_pet_param_null_id() {
        given(dao.getPetsByName('e')).willReturn([new Pet(name: 'Premier')])
        mvc.perform(get(path('/pet/?name=e')).accept(APPLICATION_JSON))
    }

    @Test(expected = NestedServletException.class)
    void get_pet_1_null_id() {
        given(dao.getPetById(1)).willReturn(new Pet(name: 'Test'))
        mvc.perform(get(path('/pet/{id}'), 1).accept(APPLICATION_JSON))
    }

    @Test
    void put_pet_null_id() {
        mvc.perform(put(path('/pet')).contentType(APPLICATION_JSON).content('{"name":"Test"}'))
                .andExpect(status().isBadRequest())
    }
}
