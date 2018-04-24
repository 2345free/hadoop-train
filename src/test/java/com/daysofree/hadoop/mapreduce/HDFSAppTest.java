package com.daysofree.hadoop.mapreduce;

import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Unit test for simple App.
 */
public class HDFSAppTest {

    private static final String HDFS_BASE = "hdfs://bigdata:9000";
    private FileSystem fileSystem;
    private Configuration configuration;

    @Test
    public void ls() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.print("isDirectory:" + fileStatus.isDirectory() + "\t");
            System.out.print("getReplication:" + fileStatus.getReplication() + "\t");
            System.out.print("getLen:" + fileStatus.getLen() + "\t");
            System.out.print("getPath:" + fileStatus.getPath() + "\t");
            System.out.println("getPermission:" + fileStatus.getPermission());
        }
    }

    @Test
    public void mkdirs() throws IOException {
        boolean mkdirs = fileSystem.mkdirs(new Path("/hdfsapi/test"));
        Assert.assertTrue("创建目录失败！", mkdirs);
    }

    @Test
    public void copyFromLocalFile() throws IOException {
        fileSystem.copyFromLocalFile(
                new Path("D:/develop/software/apache-maven-3.3.9/conf/settings-aliyun.xml"),
                new Path("/hdfsapi/test")
        );
    }

    @Test
    public void copyFromLocalFileWithProcess() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(
                        new File("D:/develop/install/bigdata/hadoop-2.6.0-cdh5.7.0.tar.gz")));

        FSDataOutputStream fsDataOutputStream = fileSystem.create(
                new Path("/hdfsapi/test/hadoop-2.6.0-cdh5.7.0.tar.gz"),
                true, 4096,
                new Progressable() {
                    @Override
                    public void progress() {
                        System.err.print("#");
                    }
                });
        IOUtils.copyBytes(bufferedInputStream, fsDataOutputStream, 4096, true);
    }

    @Test
    public void delete() throws IOException {
        boolean delete = fileSystem.delete(new Path("/hdfsapi/test/hadoop-2.6.0-cdh5.7.0.tar.gz"), true);
        Assert.assertTrue(delete);
    }

    @Before
    public void setUp() throws URISyntaxException, IOException, InterruptedException {
        // 设置客户端访问hdfs的用户名，避免出现权限错误
        System.setProperty("HADOOP_USER_NAME", "root");
        configuration = new Configuration();
        configuration.addResource("classpath:core-site.xml");
        configuration.addResource("classpath:hdfs-site.xml");
        fileSystem = FileSystem.get(new URI(HDFS_BASE), configuration, System.getProperty("HADOOP_USER_NAME"));
    }

    @After
    public void turnDown() {
        configuration = null;
        fileSystem = null;
    }

}
