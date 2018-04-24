package com.daysofree.hadoop.spring;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.hadoop.fs.FsShell;

import java.util.Collection;

/**
 * 使用spring-data-hadoop-boot
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.daysofree.hadoop")
public class SpringBootHadoopApp implements CommandLineRunner {

    @Autowired
    private FsShell fsShell;

    @Override
    public void run(String... strings) {
        Collection<FileStatus> fileStatuses = fsShell.ls("/");
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.getPath());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHadoopApp.class, args);
    }

}
