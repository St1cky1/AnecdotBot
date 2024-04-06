package ru.baksnn.project.JokeBot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.baksnn.project.JokeBot.model.JokeCall;
import ru.baksnn.project.JokeBot.service.JokeCallService;

import java.util.List;

@RestController
@AllArgsConstructor
public class JokesCallController {

    private final JokeCallService jokeCallService;

    @GetMapping("/callJokes")
    public ResponseEntity<List<JokeCall>> getJokesCalls() {
        List<JokeCall> jokeCalls = jokeCallService.allJokesCalls();
        return ResponseEntity.ok(jokeCalls);
    }
    @GetMapping("/top5")
    public ResponseEntity<List<JokeCall>> getTopFiveJokes() {
        List<JokeCall> topFiveJokes = jokeCallService.topFiveJokes();
        return ResponseEntity.ok(topFiveJokes);
    }
}