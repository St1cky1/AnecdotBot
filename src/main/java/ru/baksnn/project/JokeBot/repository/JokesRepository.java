package ru.baksnn.project.JokeBot.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baksnn.project.JokeBot.model.JokesModel;

@Repository
public interface JokesRepository extends JpaRepository<JokesModel, Long> {
    List<JokesModel> getJokesBy();
}
