package ru.baksnn.project.JokeBot.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor //Генерируем конструктор с параметрами
@NoArgsConstructor //Генерируем конструктор без параметров
@Getter //Генерируем геттеры
@Setter //Генерируем сеттеры
@ToString //Отдельный метод для toString
@Builder
@Entity(name = "jokes") //Объявляем класс как сущность для работы с ним в БД и его имя
@Table(name = "jokes") //Помечаем, как называется таблица в БД
public class JokesModel {
    @Id //Первичный ключ всему голова
    @Column(name = "id") //Название поля/столбца
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //Автогененерация значений по возрастанию - 1-2-3 и тп
    private Long id;

    @Column(name = "joke")
    private String joke;

    @Column(name = "timeCreated")
    private LocalDate timeCreated;

    @Column(name = "timeUpdated")
    private LocalDate timeUpdated;

}
