package ru.baksnn.project.JokeBot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.baksnn.project.JokeBot.model.JokesModel;
import ru.baksnn.project.JokeBot.repository.JokesRepository;

import java.time.LocalDateTime;
import java.util. *;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokesServiceImpl implements JokesService {
    private final JokesRepository jokesRepository;
    private final Random random = new Random();


    @Override
    public List<JokesModel> allJokes() {
        List<JokesModel> jokesList = jokesRepository.findAll();
        jokesList.forEach(joke -> {
        });
        return jokesList;
    }

    public Optional<JokesModel> addNewJoke(JokesModel newJoke) {
        newJoke.setTimeCreated(LocalDateTime.now());
        newJoke.setTimeUpdated(LocalDateTime.now());
        try {
            JokesModel savedJoke = jokesRepository.save(newJoke);
            return Optional.of(savedJoke);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public JokesModel updateJoke(JokesModel updatedJoke) {

        Optional<JokesModel> existingJoke = jokesRepository.findById(updatedJoke.getId());

        if (existingJoke.isPresent()) {

            JokesModel jokeToUpdate = existingJoke.get();
            jokeToUpdate.setJoke(updatedJoke.getJoke());
            jokeToUpdate.setTimeUpdated(LocalDateTime.now());

            return jokesRepository.save(jokeToUpdate);
        } else {
            throw new NoSuchElementException("Шутка с " + updatedJoke.getId() + " ID не найдена");
        }
    }
    public JokesModel deleteJoke(JokesModel deletedJoke) {
        Optional<JokesModel> existingJoke = jokesRepository.findById(deletedJoke.getId());
        if (existingJoke.isPresent()) {
            jokesRepository.deleteById(deletedJoke.getId());
            return existingJoke.get();
        } else {
            throw new NoSuchElementException("Шутка с " + deletedJoke.getId() + " ID не найдена");
        }
    }
    public Optional<JokesModel> getJokesById(Long id) {
        List<JokesModel> allJokes = allJokes();
        return allJokes.stream()
                .filter(joke -> joke.getId().equals(id))
                .findFirst();
    }
    public JokesModel getRandomJoke() {
        List<JokesModel> allJokes = jokesRepository.findAll();
        int randomIndex = random.nextInt(allJokes.size());
        return allJokes.get(randomIndex);
    }


}