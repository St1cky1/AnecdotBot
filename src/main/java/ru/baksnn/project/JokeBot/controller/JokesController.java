package ru.baksnn.project.JokeBot.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.baksnn.project.JokeBot.model.Users;
import ru.baksnn.project.JokeBot.model.Jokes;
import ru.baksnn.project.JokeBot.service.UsersServiceImpl;
import ru.baksnn.project.JokeBot.service.JokesService;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jokes")
@AllArgsConstructor
public class JokesController {
    private final JokesService service;
    private final UsersServiceImpl jokeCallService;

    @GetMapping
    public List<Jokes> allJokes() {
        return service.allJokes();
    }

    @GetMapping("/{id}")
    ResponseEntity<Jokes> getJokes(@PathVariable("id") Long id) {
        return service.getJokesById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Jokes> addNewJoke(@RequestBody Jokes jokes) {
        Optional<Jokes> newJoke = service.addNewJoke(jokes);
        return newJoke.map(jm -> ResponseEntity.status(HttpStatus.CREATED).body(jm))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jokes> updateJoke(@PathVariable Long id, @RequestBody Jokes updatedJoke) {
        Optional<Jokes> existingJoke = service.getJokesById(id);

        if (existingJoke.isPresent()) {

            Jokes jokeToUpdate = existingJoke.get();
            jokeToUpdate.setJoke(updatedJoke.getJoke());

            jokeToUpdate.setTimeUpdated(LocalDateTime.now());

            Jokes savedJoke = service.updateJoke(jokeToUpdate);

            return ResponseEntity.ok(savedJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Jokes> deleteJoke(@PathVariable Long id) {
        Optional<Jokes> deleteToJoke = service.getJokesById(id);
        if (deleteToJoke.isPresent()) {
            Jokes jokeToDelete = deleteToJoke.get();
            Jokes deleteJoke = service.deleteJoke(jokeToDelete);
            return ResponseEntity.ok(deleteJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/random")
    public Jokes getRandomJoke() {
        return service.getRandomJoke();
    }

    @GetMapping("/paged")
    public Page<Users> allJokesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return jokeCallService.getAllJokesPaged(pageable);
    }
}