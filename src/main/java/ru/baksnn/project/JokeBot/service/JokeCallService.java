package ru.baksnn.project.JokeBot.service;

import ru.baksnn.project.JokeBot.model.JokeCall;

public interface JokeCallService {
    JokeCall logJokeCall(Long userId, Long jokeId, String jokeText);
}