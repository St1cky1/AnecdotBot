package ru.baksnn.project.JokeBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.baksnn.project.JokeBot.model.JokeCall;

import java.util.List;

public interface JokeCallService {
    JokeCall logJokeCall(Long userId, Long jokeId, String jokeText);

    List<JokeCall> topFiveJokes();

    Page<JokeCall> getAllJokesPaged(Pageable pageable);

    List<JokeCall> allJokesCalls();
}