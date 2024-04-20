package ru.baksnn.project.JokeBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.baksnn.project.JokeBot.model.UsersCall;

import java.util.List;

public interface UsersCallService {
    UsersCall logJokeCall(Long userId, Long jokeId, String jokeText);

    List<UsersCall> topFiveJokes();

    Page<UsersCall> getAllJokesPaged(Pageable pageable);

    List<UsersCall> allJokesCalls();
}