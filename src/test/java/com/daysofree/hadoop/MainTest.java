package com.daysofree.hadoop;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
public class MainTest {

    static {
//        BasicConfigurator.configure();
//        try {
//            File file = ResourceUtils.getFile("classpath:log4j.properties");
//            PropertyConfigurator.configure(file.getAbsolutePath());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            ClassPathResource classPathResource = new ClassPathResource("log4j.properties");
            String path = classPathResource.getFile().getAbsolutePath();
            PropertyConfigurator.configure(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println(System.getenv("HADOOP_HOME"));

    }
}
