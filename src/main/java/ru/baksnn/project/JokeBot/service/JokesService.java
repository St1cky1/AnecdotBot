package ru.baksnn.project.JokeBot.service;


import ru.baksnn.project.JokeBot.model.JokesModel;
import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<JokesModel> allJokes();

    Optional<JokesModel> addNewJoke(JokesModel jokesModel);

    Optional<JokesModel> getJokesById(Long id);


    JokesModel updateJoke(JokesModel jokeToUpdate);

    JokesModel deleteJoke(JokesModel jokeToDelete);

    JokesModel getRandomJoke();
}