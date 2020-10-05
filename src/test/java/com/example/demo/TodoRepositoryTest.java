package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    DatabaseClient database;

    @BeforeEach
    public void setUp() {
        Hooks.onOperatorDebug();

        database.execute("DELETE FROM todo;").fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void test_Whether_FindAll_Returns_All_Rows_From_DB() {

        Todo todo1 = new Todo("Todo 1", "This is the first todo", false);
        Todo todo2 = new Todo("Todo 2", "This is the second todo", false);

        this.todoRepository.saveAll(Arrays.asList(todo1, todo2))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();

        todoRepository.findAll()
                .as(StepVerifier::create)
                .assertNext(todo1::equals)
                .assertNext(todo2::equals)
                .verifyComplete();
    }

    @Test
    public void test_Whether_Save_Inserts_Data() {
        Todo todo4 = new Todo("Todo 3", "This is the third todo", false);
        todoRepository.save(todo4)
                .as(StepVerifier::create)
                .expectNextMatches(todo -> todo.getId() != null)
                .verifyComplete();
    }

    @Test
    public void test_Whether_Delete_Removes_Data() {
        Todo todo4 = new Todo("Todo 4", "This is the fourth todo", false);

        Mono<Todo> deleted = todoRepository
                .save(todo4)
                .flatMap(saved -> todoRepository.delete(saved).thenReturn(saved));

        StepVerifier
                .create(deleted)
                .expectNextMatches(customer -> todo4.getDescription().equalsIgnoreCase("Todo 4"))
                .verifyComplete();
    }

    @Test
    public void test_Whether_Update_Changes_Flag() {
        Todo todo5 = new Todo("Todo 5", "This is the fifth todo", false);

        Mono<Todo> saved = todoRepository
                .save(todo5)
                .flatMap(todo -> {
                    todo.setDone(true);
                    return todoRepository.save(todo);
                });

        StepVerifier
                .create(saved)
                .expectNextMatches(Todo::isDone)
                .verifyComplete();
    }
}