package ru.baksnn.project.JokeBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.baksnn.project.JokeBot.model.ClientsRole;


public interface ClientsRolesRepository extends CrudRepository<ClientsRole, Long> {
}