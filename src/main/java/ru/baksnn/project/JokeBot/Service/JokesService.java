package ru.baksnn.project.JokeBot.Service;


import org.springframework.stereotype.Service;
import ru.baksnn.project.JokeBot.Model.JokesModel;
import java.util.List;
import java.util.Optional;

public interface JokesService {
    List<JokesModel> AllJokes();

    Optional<JokesModel> addNewJoke(String json);

    Optional<JokesModel> getJokesById(Long id);


    JokesModel updateJoke(JokesModel jokeToUpdate);

    JokesModel deleteJoke(JokesModel jokeToDelete);
}