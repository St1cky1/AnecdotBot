package ru.baksnn.project.JokeBot.service;

import ru.baksnn.project.JokeBot.model.JokeCall;

import java.util.List;
import java.util.Optional;


public interface JokeCallService {
    JokeCall logJokeCall(Long userId, Long jokeId, String jokeText);

    List<JokeCall> topFiveJokes();

    List<JokeCall> allJokesCalls();
}