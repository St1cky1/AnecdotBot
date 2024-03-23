package ru.baksnn.project.JokeBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.baksnn.project.JokeBot.model.JokesModel;
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
        List<JokesModel> mockJokesList = new ArrayList<>();
        mockJokesList.add(new JokesModel(1l,"Test Joke 1",new Date(),new Date()));
        mockJokesList.add(new JokesModel(1L,"Test Joke 2",new Date(),new Date()));

        when(jokesRepository.findAll()).thenReturn(mockJokesList);

        List<JokesModel> result = jokesService.allJokes();

        assertEquals(mockJokesList.size(), result.size());
        assertEquals(mockJokesList, result);
    }

    @Test
    void addNewJoke_returnSavedJoke() {
        JokesModel newJoke = new JokesModel(1L,"New Joke",new Date(),new Date());

        when(jokesRepository.save(any())).thenReturn(newJoke);

        Optional<JokesModel> result = jokesService.addNewJoke(newJoke);

        assertTrue(result.isPresent());
        assertEquals(newJoke, result.get());
    }

    @Test
    void updateJoke_existingJoke_returnUpdatedJoke() {
        JokesModel existingJoke = new JokesModel(1L, "Existing Joke",new Date(),new Date());
        JokesModel updatedJoke = new JokesModel(1L, "Updated Joke",new Date(),new Date());

        when(jokesRepository.findById(existingJoke.getId())).thenReturn(Optional.of(existingJoke));
        when(jokesRepository.save(any())).thenReturn(updatedJoke);

        JokesModel result = jokesService.updateJoke(updatedJoke);

        assertEquals(updatedJoke, result);
    }

    @Test
    void updateJoke_nonExistingJoke_throwNoSuchElementException() {
        JokesModel nonExistingJoke = new JokesModel(1L, "Non Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(nonExistingJoke.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> jokesService.updateJoke(nonExistingJoke));
    }

    @Test
    void deleteJoke_existingJoke_returnDeletedJoke() {
        JokesModel existingJoke = new JokesModel(1L, "Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(existingJoke.getId())).thenReturn(Optional.of(existingJoke));

        JokesModel result = jokesService.deleteJoke(existingJoke);

        assertEquals(existingJoke, result);
    }

    @Test
    void deleteJoke_nonExistingJoke_throwNoSuchElementException() {
        JokesModel nonExistingJoke = new JokesModel(1L, "Non Existing Joke",new Date(),new Date());

        when(jokesRepository.findById(nonExistingJoke.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> jokesService.deleteJoke(nonExistingJoke));
    }

    @Test
    void getJokesById_existingJoke_returnJokeOptional() {
        JokesModel existingJoke = new JokesModel(1L, "Existing Joke",new Date(),new Date());
        List<JokesModel> allJokes = new ArrayList<>();
        allJokes.add(existingJoke);

        when(jokesService.allJokes()).thenReturn(allJokes);

        Optional<JokesModel> result = jokesService.getJokesById(existingJoke.getId());

        assertTrue(result.isPresent());
        assertEquals(existingJoke, result.get());
    }

    @Test
    void getJokesById_nonExistingJoke_returnEmptyOptional() {
        JokesModel nonExistingJoke = new JokesModel(1L, "Non Existing Joke",new Date(),new Date());
        List<JokesModel> allJokes = new ArrayList<>();

        when(jokesRepository.findAll()).thenReturn(allJokes);

        Optional<JokesModel> result = jokesService.getJokesById(nonExistingJoke.getId());

        assertFalse(result.isPresent());
    }

}
