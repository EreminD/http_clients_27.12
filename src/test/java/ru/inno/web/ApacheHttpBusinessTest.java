package ru.inno.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.web.model.Task;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApacheHttpBusinessTest {
    private static final String URL = "https://todo-app-sky.herokuapp.com";

    private TaskService service;

    @BeforeEach
    public void setUp() {
        service = new TaskService(URL);
    }

    @Test
    public void iCanCrateTheFirstTask() throws IOException {
        service.deleteAllTasks();
        Task newTask = service.createNewTask("My new task");
        List<Task> list = service.getTaskList();

        assertTrue(list.contains(newTask));
    }

    @Test
    public void iCanDeleteAllTasks() throws IOException {
        service.createNewTask();
        service.createNewTask();
        service.createNewTask();
        service.createNewTask();
        service.createNewTask();
        service.deleteAllTasks();
        List<Task> taskList = service.getTaskList();
        assertEquals(0, taskList.size());
    }
}
