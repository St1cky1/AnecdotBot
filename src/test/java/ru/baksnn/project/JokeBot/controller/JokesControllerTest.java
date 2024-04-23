package ru.baksnn.project.JokeBot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.baksnn.project.JokeBot.model.Jokes;
import ru.baksnn.project.JokeBot.service.JokesService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(JokesController.class)
public class JokesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokesService jokesService;

    @InjectMocks
    private JokesController jokesController;

    @Test
    public void testAllJokes() throws Exception {
        given(jokesService.allJokes()).willReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/jokes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetJokesById() throws Exception {
        Long id = 1L;
        Jokes joke = new Jokes();
        given(jokesService.getJokesById(id)).willReturn(Optional.of(joke));

        mockMvc.perform(MockMvcRequestBuilders.get("/jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
    @Test
    public void testAddNewJoke() throws Exception {
        Jokes joke = new Jokes();
        given(jokesService.addNewJoke(joke)).willReturn(Optional.of(joke));

        mockMvc.perform(MockMvcRequestBuilders.post("/jokes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(joke)));
    }

    @Test
    public void testUpdateJoke() throws Exception {
        Long id = 1L;
        Jokes updatedJoke = new Jokes();
        given(jokesService.getJokesById(id)).willReturn(Optional.of(new Jokes()));
        given(jokesService.updateJoke(any())).willReturn(updatedJoke);

        mockMvc.perform(MockMvcRequestBuilders.put("/jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedJoke)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteJoke() throws Exception {
        Long id = 1L;
        Jokes joke = new Jokes();
        given(jokesService.getJokesById(id)).willReturn(Optional.of(joke));
        given(jokesService.deleteJoke(any())).willReturn(joke);

        mockMvc.perform(MockMvcRequestBuilders.delete("/jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}