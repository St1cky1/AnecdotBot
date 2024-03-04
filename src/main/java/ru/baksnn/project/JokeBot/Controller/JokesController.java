package ru.baksnn.project.JokeBot.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.baksnn.project.JokeBot.Model.JokesModel;
import ru.baksnn.project.JokeBot.Service.JokesService;


import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("jokes")
@AllArgsConstructor
public class JokesController {
    private final JokesService service;
    @GetMapping
    public List<JokesModel> AllJokes() {
        return service.AllJokes();
    }

    @GetMapping("/{id}")
    ResponseEntity<JokesModel> getJokes(@PathVariable("id") Long id) {
        return service.getJokesById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        // из-за того что не было builder'a в модели не работали get и set
    }

    @PostMapping
    public ResponseEntity<JokesModel> addNewJoke(@RequestBody String json) {
        Optional<JokesModel> newJoke = service.addNewJoke(json);
        return newJoke.map(jokesModel -> ResponseEntity.status(HttpStatus.CREATED).body(jokesModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<JokesModel> updateJoke(@PathVariable Long id, @RequestBody JokesModel updatedJoke) {
        Optional<JokesModel> existingJoke = service.getJokesById(id);

        if (existingJoke.isPresent()) {

            JokesModel jokeToUpdate = existingJoke.get();
            jokeToUpdate.setJoke(updatedJoke.getJoke());

            jokeToUpdate.setTimeUpdated(LocalDate.now());

            JokesModel savedJoke = service.updateJoke(jokeToUpdate);

            return ResponseEntity.ok(savedJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<JokesModel> deleteJoke(@PathVariable Long id, @RequestBody JokesModel deletedJoke) {
        Optional<JokesModel> deleteToJoke = service.getJokesById(id);
        if (deleteToJoke.isPresent()) {
            JokesModel jokeToDelete = deleteToJoke.get();
            jokeToDelete.setJoke(deletedJoke.getJoke());
            JokesModel deleteJoke = service.deleteJoke(jokeToDelete);
            return ResponseEntity.ok(deleteJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


