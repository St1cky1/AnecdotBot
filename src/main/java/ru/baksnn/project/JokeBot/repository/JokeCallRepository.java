package ru.baksnn.project.JokeBot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baksnn.project.JokeBot.model.JokeCall;

@Repository
public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {
    Page<JokeCall> findAll(Pageable pageable);
}