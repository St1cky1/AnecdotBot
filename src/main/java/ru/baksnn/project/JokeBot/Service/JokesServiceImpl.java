    package ru.baksnn.project.JokeBot.Service;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import ru.baksnn.project.JokeBot.Model.JokesModel;
    import ru.baksnn.project.JokeBot.Repository.JokesRepository;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util. *;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class JokesServiceImpl implements JokesService {
        private final JokesRepository jokesRepository;
        @Override
        public List<JokesModel> AllJokes() {

            List<JokesModel> jokesList = jokesRepository.findAll();

            LocalDate currentDate = LocalDate.now();
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

                // Сохранение новой шутки
                JokesModel savedJoke = jokesRepository.save(newJoke);

                return Optional.of(savedJoke);
            } catch (IOException e) {
                e.printStackTrace(); // обработка ошибок парсинга JSON
                return Optional.empty();
            } catch (Exception e) {
                e.printStackTrace(); // обработка других возможных ошибок при сохранении
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
                throw new NoSuchElementException("Joke with ID " + updatedJoke.getId() + " not found");
            }
        }
        public JokesModel deleteJoke(JokesModel deletedJoke) {
            Optional<JokesModel> existingJoke = jokesRepository.findById(deletedJoke.getId());

            if (existingJoke.isPresent()) {
                jokesRepository.deleteById(deletedJoke.getId());
                return existingJoke.get();
            } else {
                throw new NoSuchElementException("Joke with ID " + deletedJoke.getId() + " not found");
            }
        }

        public Optional<JokesModel> getJokesById(Long id) {
            List<JokesModel> allJokes = AllJokes();
            return allJokes.stream()
                    .filter(joke -> joke.getId().equals(id))
                    .findFirst();
        }
    }
