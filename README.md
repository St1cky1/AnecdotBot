# Анекдотичекий чат-бот в Telegram 

### Этот проект представляет собой чат-бота для Telegram, который предоставляет пользователю анекдоты различных жанров. Бот написан на Java с использованием фреймворка Spring Boot.

## Добавленные изменения:
- Подключена база данных PostgreSQL
- Переведена схема генерация базы данных на Liquebase
-  Добавлена дополнительная сущность: вызовы анекдота. В ней содержатся айди, время вызова шутки, айди шутки, текст шутки, айди пользователя, который вызвал шутку

![image](https://github.com/BakSnn/Joke-Bot/assets/112386046/e9a6e131-acee-4988-8b87-623995b9e3ea)

-  Добавлен еще один метод в API - топ 5 популярных анекдотов (/callJokes - выводет все вызванные шутки, /top5 - выводит список самых популярных вызванных шуток)

![320253868-290ab0f1-f926-4891-87cd-1964ee3344eb](https://github.com/BakSnn/Joke-Bot/assets/112386046/35a13094-b812-49dd-97a3-3056dfd04128)
- Добавлен метод полноценного рандома с выбором шуток из БД

![image](https://github.com/BakSnn/Joke-Bot/assets/112386046/11db1a0f-7318-4b3c-a196-208761f82a38)

## Реализовано:
####  "Admin Panel" для управления шутками
- Выдача всех шуток
- Добавление новой шутки
- Выдача шутки по ID
- Изменение шутки по ID
- Удаление шутки по ID

![Снимок экрана 2024-03-16 113852](https://github.com/BakSnn/Joke-Bot/assets/112386046/8d7e5d83-bf37-462a-bf07-e17563d7a103)
#### Взаимодействие с Telegram - ботом. Выдача шуток из БД по нажатию кнопки (https://t.me/JokesVeryFun_bot)
