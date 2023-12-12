package com.example.bootproject;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.*;

public class ServletInitializer extends SpringBootServletInitializer {

    private static final List<String> VACATION_REQUEST_LIST = Collections.synchronizedList(new ArrayList<>());
    private static final List<String> APPEAL_REQUEST_LIST = Collections.synchronizedList(new ArrayList<>());
    private static final List<String> VACATION_PROCESS_LIST = Collections.synchronizedList(new ArrayList<>());
    private static final List<String> APPEAL_PROCESS_LIST = Collections.synchronizedList(new ArrayList<>());
    public static Map<String, List<String>> REQUEST_LIST = new HashMap<>(Map.of("/employee/vacation", VACATION_REQUEST_LIST, "/employee/appeal", APPEAL_REQUEST_LIST, "/manager/vacation/process", VACATION_PROCESS_LIST,"/manager/appeal/process", APPEAL_PROCESS_LIST));

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootProjectApplication.class);
    }

}
