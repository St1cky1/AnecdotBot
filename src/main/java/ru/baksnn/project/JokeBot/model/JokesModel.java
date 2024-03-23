package ru.baksnn.project.JokeBot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "jokes") 
@Table(name = "jokes")
public class JokesModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "joke")
    private String joke;

    @Column(name = "timeCreated")
    private Date timeCreated;

    @Column(name = "timeUpdated")
    private Date timeUpdated;

}
