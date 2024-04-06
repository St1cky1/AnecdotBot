package ru.baksnn.project.JokeBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.baksnn.project.JokeBot.model.JokeCall;

import java.util.List;
import java.util.Optional;


public interface JokeCallService {
    JokeCall logJokeCall(Long userId, Long jokeId, String jokeText);

    List<JokeCall> topFiveJokes();

    Page<JokeCall> getAllJokesPaged(Pageable pageable);

    List<JokeCall> allJokesCalls();
}