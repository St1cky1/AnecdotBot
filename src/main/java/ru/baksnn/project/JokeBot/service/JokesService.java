package ru.baksnn.project.JokeBot.service;


import ru.baksnn.project.JokeBot.model.JokesModel;
import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<JokesModel> AllJokes();

    Optional<JokesModel> addNewJoke(String json);

    Optional<JokesModel> getJokesById(Long id);


    JokesModel updateJoke(JokesModel jokeToUpdate);

    JokesModel deleteJoke(JokesModel jokeToDelete);
}