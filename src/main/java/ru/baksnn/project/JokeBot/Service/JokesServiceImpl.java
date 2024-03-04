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
            // Retrieve all jokes from the database
            List<JokesModel> jokesList = jokesRepository.findAll();

            // Modify the retrieved jokes if needed
            LocalDate currentDate = LocalDate.now();
            jokesList.forEach(joke -> {
                joke.setTimeUpdated(currentDate);
                // You can perform additional modifications if necessary
            });

            return jokesList;
        }

        public Optional<JokesModel> addNewJoke(String json) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JokesModel newJoke = objectMapper.readValue(json, JokesModel.class);

                // Дополнительная логика, если необходимо, например, установка времени создания и обновления.
                newJoke.setTimeCreated(LocalDate.now());
                newJoke.setTimeUpdated(LocalDate.now());

                // Сохранение новой шутки в базе данных или ее добавление в список, в зависимости от вашей логики.
                JokesModel savedJoke = jokesRepository.save(newJoke);

                return Optional.of(savedJoke);
            } catch (IOException e) {
                e.printStackTrace(); // Обработка ошибок парсинга JSON.
                return Optional.empty();
            } catch (Exception e) {
                e.printStackTrace(); // Обработка других возможных ошибок при сохранении.
                return Optional.empty();
            }
        }
        public JokesModel updateJoke(JokesModel updatedJoke) {
            // Assuming your JokesRepository extends JpaRepository
            Optional<JokesModel> existingJoke = jokesRepository.findById(updatedJoke.getId());

            if (existingJoke.isPresent()) {
                // Update the fields you want to modify
                JokesModel jokeToUpdate = existingJoke.get();
                jokeToUpdate.setJoke(updatedJoke.getJoke());
                jokeToUpdate.setTimeUpdated(LocalDate.now());

                // Save the updated joke
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
