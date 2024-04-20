package ru.baksnn.project.JokeBot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.baksnn.project.JokeBot.model.UsersCall;

@Repository
public interface UsersCallRepository extends JpaRepository<UsersCall, Long> {
    Page<UsersCall> findAll(Pageable pageable);
}