package ru.baksnn.project.JokeBot.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.baksnn.project.JokeBot.model.Clients;


public interface ClientsRepository extends CrudRepository<Clients, Long> {
    Optional<Clients> findByUsername(String username);
}