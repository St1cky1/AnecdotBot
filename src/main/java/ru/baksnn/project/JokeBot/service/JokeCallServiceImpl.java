package ru.baksnn.project.JokeBot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.baksnn.project.JokeBot.model.JokeCall;
import ru.baksnn.project.JokeBot.repository.JokeCallRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<JokeCall> topFiveJokes(){
        Map<Long, List<JokeCall>> groupedJokes = jokesCallRepository.findAll().stream()
                .collect(Collectors.groupingBy(JokeCall::getJokeId));

        return groupedJokes.values().stream()
                .map(jokeCalls -> jokeCalls.get(0))
                .sorted(Comparator.comparing(jokeCall -> groupedJokes.get(jokeCall.getJokeId()).size(), Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<JokeCall> allJokesCalls() {
        return jokesCallRepository.findAll();
    }
}