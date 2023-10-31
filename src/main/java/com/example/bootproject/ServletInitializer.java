package com.example.bootproject;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.*;

public class ServletInitializer extends SpringBootServletInitializer {

    private static List<String> VACATION_REQUEST_LIST = Collections.synchronizedList(new ArrayList<>());
    private static List<String> APPEAL_REQUEST_LIST = Collections.synchronizedList(new ArrayList<>());
    public static Map<String, List<String>> REQUEST_LIST = new HashMap<>(Map.of("/employee/vacation", VACATION_REQUEST_LIST, "/employee/appeal", APPEAL_REQUEST_LIST));

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootProjectApplication.class);
    }

}
