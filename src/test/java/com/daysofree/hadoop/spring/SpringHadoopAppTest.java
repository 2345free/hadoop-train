package com.daysofree.hadoop.spring;

import com.alibaba.fastjson.JSON;
import com.daysofree.hadoop.BaseTest;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * 使用spring-data-hadoop
 */
@Slf4j
public class SpringHadoopAppTest extends BaseTest {

    @Autowired
    private FileSystem fileSystem;

    @Autowired
    private HbaseTemplate hbaseTemplate;

    static {
        System.setProperty("hadoop.home.dir", "D:/develop/software/hadoop-common-2.2.0-bin-master");
    }

    @Test
    public void hbasePutData() {

        hbaseTemplate.execute("test", new TableCallback<Object>() {
            @Override
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes("row1"));
                p.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("a"), Bytes.toBytes("value1"));
                p.addColumn(Bytes.toBytes("cf2"), Bytes.toBytes("b"), Bytes.toBytes("value2"));
                table.put(p);
                return null;
            }
        });
    }

    @Test
    public void userAgentCount() throws IOException {
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path("/input/10000_access.log"), 4096);
        InputStreamReader inputStreamReader = new InputStreamReader(fsDataInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String logStr;
        HashMap<String, Integer> count = new HashMap<>();
        while ((logStr = bufferedReader.readLine()) != null) {
            int point = 0;
            for (int i = 0; i <= 6; i++) {
                point = logStr.indexOf("\"", point + 1);
            }
            String userAgentStr = logStr.substring(point, logStr.indexOf("\"", point + 1));
            log.info(userAgentStr);
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
            log.info(Objects.toString(userAgent));
            String name = userAgent.getBrowser().getGroup().getName();
            Integer num = count.get(name);
            if (num == null) {
                count.put(name, 1);
            } else {
                count.put(name, ++num);
            }
        }
        log.info(JSON.toJSONString(count, true));
    }

    @Test
    public void testLs() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.getPath());
        }
    }

    @Test
    public void mkdirs() throws IOException {
        fileSystem.mkdirs(new Path("/input/spring"), null);
    }

    @Before
    public void setUp() {

    }

    @After
    public void turnDown() {
        fileSystem = null;
    }


}
