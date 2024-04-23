package ru.baksnn.project.JokeBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baksnn.project.JokeBot.model.Jokes;

@Repository
public interface JokesRepository extends JpaRepository<Jokes, Long> {

}
