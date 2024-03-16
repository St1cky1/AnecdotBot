    package ru.baksnn.project.JokeBot.service;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import ru.baksnn.project.JokeBot.model.JokesModel;
    import ru.baksnn.project.JokeBot.repository.JokesRepository;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util. *;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class JokesServiceImpl implements JokesService {
        private final JokesRepository jokesRepository;
        @Override
        public List<JokesModel> allJokes() {
            List<JokesModel> jokesList = jokesRepository.findAll();
            jokesList.forEach(joke -> {
            });
            return jokesList;
        }

        public Optional<JokesModel> addNewJoke(String json) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JokesModel newJoke = objectMapper.readValue(json, JokesModel.class);
                newJoke.setTimeCreated(LocalDate.now());
                newJoke.setTimeUpdated(LocalDate.now());

                JokesModel savedJoke = jokesRepository.save(newJoke);

                return Optional.of(savedJoke);
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
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
                jokeToUpdate.setTimeUpdated(LocalDate.now());

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
    }
