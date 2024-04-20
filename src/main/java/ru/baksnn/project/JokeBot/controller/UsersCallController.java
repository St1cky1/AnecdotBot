package ru.baksnn.project.JokeBot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.baksnn.project.JokeBot.model.UsersCall;
import ru.baksnn.project.JokeBot.service.UsersCallService;

import java.util.List;

@RestController
@AllArgsConstructor
public class UsersCallController {

    private final UsersCallService usersCallService;

    @GetMapping("/callJokes")
    public ResponseEntity<List<UsersCall>> getJokesCalls() {
        List<UsersCall> usersCalls = usersCallService.allJokesCalls();
        return ResponseEntity.ok(usersCalls);
    }
    @GetMapping("/top5")
    public ResponseEntity<List<UsersCall>> getTopFiveJokes() {
        List<UsersCall> topFiveJokes = usersCallService.topFiveJokes();
        return ResponseEntity.ok(topFiveJokes);
    }
}