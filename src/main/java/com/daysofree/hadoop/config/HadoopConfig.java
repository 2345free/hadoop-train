package com.daysofree.hadoop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.config.annotation.EnableHadoop;
import org.springframework.data.hadoop.config.annotation.SpringHadoopConfigurerAdapter;
import org.springframework.data.hadoop.config.annotation.builders.HadoopConfigConfigurer;

@Configuration
@EnableHadoop
public class HadoopConfig extends SpringHadoopConfigurerAdapter {

    @Value("${spring.hadoop.fsUri}")
    private String fileSystemUri;

    @Override
    public void configure(HadoopConfigConfigurer config) throws Exception {
        config.fileSystemUri(fileSystemUri)
                .withResources().resource("core-site.xml").resource("hdfs-site.xml");
    }

    
}
