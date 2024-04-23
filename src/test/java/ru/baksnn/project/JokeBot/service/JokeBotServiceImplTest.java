package ru.baksnn.project.JokeBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.baksnn.project.JokeBot.model.Jokes;
import ru.baksnn.project.JokeBot.repository.JokesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JokesServiceImplTest {

    @Mock
    private JokesRepository jokesRepository;

    @InjectMocks
    private JokesServiceImpl jokesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void allJokes_returnListOfJokes() {
        List<Jokes> mockJokesList = new ArrayList<>();
        mockJokesList.add(new Jokes(1l,"Test Joke 1",new Date(),new Date()));
        mockJokesList.add(new Jokes(1L,"Test Joke 2",new Date(),new Date()));

        when(jokesRepository.findAll()).thenReturn(mockJokesList);

        List<Jokes> result = jokesService.allJokes();

        assertEquals(mockJokesList.size(), result.size());
        assertEquals(mockJokesList, result);
    }

    @Test
    void addNewJoke_returnSavedJoke() {
        Jokes newJoke = new Jokes(1L,"New Joke",new Date(),new Date());

        when(jokesRepository.save(any())).thenReturn(newJoke);

        Optional<Jokes> result = jokesService.addNewJoke(newJoke);

        assertTrue(result.isPresent());
        assertEquals(newJoke, result.get());
    }

    @Test
    void updateJoke_existingJoke_returnUpdatedJoke() {
        Jokes existingJoke = new Jokes(1L, "Existing Joke",new Date(),new Date());
        Jokes updatedJoke = new Jokes(1L, "Updated Joke",new Date(),new Date());

        when(jokesRepository.findById(existingJoke.getId())).thenReturn(Optional.of(existingJoke));
        when(jokesRepository.save(any())).thenReturn(updatedJoke);

        Jokes result = jokesService.updateJoke(updatedJoke);

        assertEquals(updatedJoke, result);
    }

    @Test
    void updateJoke_nonExistingJoke_throwNoSuchElementException() {
        Jokes nonExistingJoke = new Jokes(1L, "Non Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(nonExistingJoke.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> jokesService.updateJoke(nonExistingJoke));
    }

    @Test
    void deleteJoke_existingJoke_returnDeletedJoke() {
        Jokes existingJoke = new Jokes(1L, "Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(existingJoke.getId())).thenReturn(Optional.of(existingJoke));

        Jokes result = jokesService.deleteJoke(existingJoke);

        assertEquals(existingJoke, result);
    }

    @Test
    void deleteJoke_nonExistingJoke_throwNoSuchElementException() {
        Jokes nonExistingJoke = new Jokes(1L, "Non Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(nonExistingJoke.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> jokesService.deleteJoke(nonExistingJoke));
    }

    @Test
    void getJokesById_existingJoke_returnJokeOptional() {
        Jokes existingJoke = new Jokes(1L, "Existing Joke",new Date(),new Date());
        List<Jokes> allJokes = new ArrayList<>();
        allJokes.add(existingJoke);

        when(jokesService.allJokes()).thenReturn(allJokes);

        Optional<Jokes> result = jokesService.getJokesById(existingJoke.getId());

        assertTrue(result.isPresent());
        assertEquals(existingJoke, result.get());
    }

    @Test
    void getJokesById_nonExistingJoke_returnEmptyOptional() {
        Jokes nonExistingJoke = new Jokes(1L, "Non Existing Joke",new Date(),new Date());
        List<Jokes> allJokes = new ArrayList<>();

        when(jokesRepository.findAll()).thenReturn(allJokes);

        Optional<Jokes> result = jokesService.getJokesById(nonExistingJoke.getId());

        assertFalse(result.isPresent());
    }

}
