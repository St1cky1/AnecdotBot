package ru.baksnn.project.JokeBot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.baksnn.project.JokeBot.model.UsersCall;
import ru.baksnn.project.JokeBot.repository.UsersCallRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersCallServiceImpl implements UsersCallService {

    private final UsersCallRepository jokesCallRepository;

    @Override
    public UsersCall logJokeCall(Long userId, Long jokeId, String jokeText) {
        UsersCall usersCall = new UsersCall();
        usersCall.setUserId(userId);
        usersCall.setJokeId(jokeId);
        usersCall.setJokeText(jokeText);
        usersCall.setCallTime(LocalDateTime.now());
        return jokesCallRepository.save(usersCall);
    }

    @Override
    public List<UsersCall> topFiveJokes(){
        Map<Long, List<UsersCall>> groupedJokes = jokesCallRepository.findAll().stream()
                .collect(Collectors.groupingBy(UsersCall::getJokeId));

        return groupedJokes.values().stream()
                .map(jokeCalls -> jokeCalls.get(0))
                .sorted(Comparator.comparing(jokeCall -> groupedJokes.get(jokeCall.getJokeId()).size(), Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsersCall> getAllJokesPaged(Pageable pageable) {
        return jokesCallRepository.findAll(pageable);
    }

    @Override
    public List<UsersCall> allJokesCalls() {
        return jokesCallRepository.findAll();
    }
}