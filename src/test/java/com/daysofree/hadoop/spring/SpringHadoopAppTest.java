package com.daysofree.hadoop.spring;

import com.daysofree.hadoop.BaseTest;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 使用spring-data-hadoop
 */
public class SpringHadoopAppTest extends BaseTest {

    @Resource
    private FileSystem fileSystem;

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
