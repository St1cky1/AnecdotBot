package ru.baksnn.project.JokeBot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.baksnn.project.JokeBot.model.JokeCall;
import ru.baksnn.project.JokeBot.repository.JokeCallRepository;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JokeCallServiceImpl implements JokeCallService {

        private final JokeCallRepository jokesCallRepository;

        @Override
        public JokeCall logJokeCall(Long userId, Long jokeId, String jokeText) {
            JokeCall jokeCall = new JokeCall();
            jokeCall.setUserId(userId);
            jokeCall.setJokeId(jokeId);
            jokeCall.setJokeText(jokeText);
            jokeCall.setCallTime(LocalDateTime.now());
            return jokesCallRepository.save(jokeCall);
        }
}