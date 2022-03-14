package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Controller
    public static class App {

        @RequestMapping({"", "/"})
        public String home() throws Exception {
            return "home";
        }

        @RequestMapping("/crash")
        @ResponseBody
        public String crash() throws Exception {
            try (Reader in = new InputStreamReader(
                    new FileInputStream("/path/to/non/existent/file"), StandardCharsets.UTF_8)) {
                char[] buffer = new char[4096];
                int read;
                StringBuilder string = new StringBuilder();
                while ((read = in.read(buffer)) >= 0) {
                    string.append(buffer, 0, read);
                }
                return string.toString();
            }
        }
    }

}
