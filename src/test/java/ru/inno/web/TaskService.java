package ru.inno.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import ru.inno.web.model.Task;

import java.io.IOException;
import java.util.List;

public class TaskService {
    private HttpClient client;
    private final String URL;
    public TaskService(String url) {
        URL = url;
        this.client = HttpClientBuilder.create().build();
    }

    public void deleteAllTasks() throws IOException {
        HttpDelete deleteAll = new HttpDelete(URL);
        client.execute(deleteAll);
    }

    public Task createNewTask() throws IOException {
        return createNewTask( "to be deleted");
    }
    public Task createNewTask(String title) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpPost createTask = new HttpPost(URL);
        String myContent = "{\"title\" : \""+title+"\"}";
        StringEntity entity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        createTask.setEntity(entity);
        HttpResponse createResponse = client.execute(createTask);
        String responseBody = EntityUtils.toString(createResponse.getEntity());
        return mapper.readValue(responseBody, Task.class);
    }

    public List<Task> getTaskList() throws IOException {
        HttpGet getAll = new HttpGet(URL);
        HttpResponse taskList = client.execute(getAll);
        String responseBody = EntityUtils.toString(taskList.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody, new TypeReference<>() {});
    }
}
