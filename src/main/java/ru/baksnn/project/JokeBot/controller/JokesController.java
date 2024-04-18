package ru.baksnn.project.JokeBot.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.baksnn.project.JokeBot.model.JokeCall;
import ru.baksnn.project.JokeBot.model.JokesModel;
import ru.baksnn.project.JokeBot.service.JokeCallServiceImpl;
import ru.baksnn.project.JokeBot.service.JokesService;

import java.util.Date;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jokes")
@AllArgsConstructor
public class JokesController {
    private final JokesService service;
    private final JokeCallServiceImpl jokeCallService;

    @GetMapping
    public List<JokesModel> allJokes() {
        return service.allJokes();
    }

    @GetMapping("/{id}")
    ResponseEntity<JokesModel> getJokes(@PathVariable("id") Long id) {
        return service.getJokesById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JokesModel> addNewJoke(@RequestBody JokesModel jokesModel) {
        Optional<JokesModel> newJoke = service.addNewJoke(jokesModel);
        return newJoke.map(jm -> ResponseEntity.status(HttpStatus.CREATED).body(jm))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JokesModel> updateJoke(@PathVariable Long id, @RequestBody JokesModel updatedJoke) {
        Optional<JokesModel> existingJoke = service.getJokesById(id);

        if (existingJoke.isPresent()) {

            JokesModel jokeToUpdate = existingJoke.get();
            jokeToUpdate.setJoke(updatedJoke.getJoke());

            jokeToUpdate.setTimeUpdated(new Date());

            JokesModel savedJoke = service.updateJoke(jokeToUpdate);

            return ResponseEntity.ok(savedJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JokesModel> deleteJoke(@PathVariable Long id) {
        Optional<JokesModel> deleteToJoke = service.getJokesById(id);
        if (deleteToJoke.isPresent()) {
            JokesModel jokeToDelete = deleteToJoke.get();
            JokesModel deleteJoke = service.deleteJoke(jokeToDelete);
            return ResponseEntity.ok(deleteJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/random")
    public JokesModel getRandomJoke() {
        return service.getRandomJoke();
    }

    @GetMapping("/paged")
    public Page<JokeCall> allJokesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return jokeCallService.getAllJokesPaged(pageable);
    }
}