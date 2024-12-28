package ru.itmo.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@ApplicationPath("/api")
public class SoaApplication extends Application {
}
