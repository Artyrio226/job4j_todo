# Приложение "TODO список"

## Сайт для ведения списка заданий.

### Основные функции:
- Вывод страницы со списком всех заданий 
  с возможностью отбора (все / выполненные / новые) и добавления заданий.
- Вывод страницы с подробным описанием задания, выполнение и удаление.
- Вывод страницы с редактированием задания.

### Стек:
- Java 17
- Spring Boot 2.7.3
- Thymeleaf
- Bootstrap
- PostgreSQL 16.2
- Liquibase 4.15.0
- Hibernate 5.6.11
- Lombok 1.18.22

### Требования к окружению
- Java 17
- Maven 3.8
- PostgreSQL 15

### Как запустить:
1. Создать новую базу данных **todo**.
2. Перейти в папку с проектом.
3. Скорректировать настройки подключения к БД **todo** в файлах 
   **db/liquibase.properties** и **src/main/resources/hibernate.cfg.xml**.
4. Создать схему базы данных: `mvn -P production liquibase:update`
5. Создать jar: `mvn package`
6. Запустить программу: `java -jar target/job4j_todo-1.0-SNAPSHOT.jar`
7. Перейти в браузере по адресу: http://localhost:8080/

### Контакты:

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/Artyrio226)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:artur_sar_80@mail.ru)

### Скриншоты:

- [x] Главная страница.

  ![](/files/readme/1.png)

- [x] Выполненные.

  ![](/files/readme/2.png)

- [x] Новые.

  ![](/files/readme/3.png)

- [x] Добавление задания.

  ![](/files/readme/4.png)

- [x] Подробное описание.

  ![](/files/readme/5.png)

- [x] Редактирование задания.

  ![](/files/readme/6.png)

- [x] Выполнено.

  ![](/files/readme/7.png)
