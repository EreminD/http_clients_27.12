package ru.inno.web.extention;

import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MyFileLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(@NotNull String s) {
        try {
            Files.writeString(Path.of("log.log"), s, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
