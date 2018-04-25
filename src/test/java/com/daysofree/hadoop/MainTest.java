package com.daysofree.hadoop;

import eu.bitwalker.useragentutils.UserAgent;
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

//        String userAgentStr = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
//        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
//        log.info(userAgent.getBrowser());
//        log.info(userAgent.getBrowserVersion());
//        log.info(userAgent.getOperatingSystem());

        // nginx的一条访问日志信息
        String logStr = " 182.138.128.41 - - [10/Nov/2016:00:01:36 +0800] \"GET /u/card HTTP/1.1\" 200 330 \"www.imooc.com\" \"http://www.imooc.com/video/11239\" - \"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\" \"-\" 10.100.136.65:80 200 0.065 0.065";
        int point = 0;
        for (int i = 0; i <= 6; i++) {
            point = logStr.indexOf("\"", point + 1);
        }
        String userAgentStr = logStr.substring(point, logStr.indexOf("\"", point + 1));
        log.info(userAgentStr);
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        log.info(userAgent.getBrowser().toString());
        log.info(userAgent.getBrowserVersion().toString());
        log.info(userAgent.getOperatingSystem().toString());


        String a = "a";
        System.out.println(a.substring(0, a.indexOf(1)));

    }
}
