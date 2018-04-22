package com.daysofree.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        RemoteIterator<FileStatus> fileStatusRemoteIterator = fileSystem.listStatusIterator(new Path("/"));
        while (fileStatusRemoteIterator.hasNext()) {
            FileStatus fileStatus = fileStatusRemoteIterator.next();
            System.out.print("isDirectory:" + fileStatus.isDirectory() + "\t");
            System.out.print("getReplication:" + fileStatus.getReplication() + "\t");
            System.out.print("getLen:" + fileStatus.getLen() + "\t");
            System.out.print("getPath:" + fileStatus.getPath() + "\t");
            System.out.println("getPermission:" + fileStatus.getPermission());
        }
    }


    @Before
    public void turnUp() throws URISyntaxException, IOException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_BASE), configuration);
    }

    @After
    public void turnDown() {
        configuration = null;
        fileSystem = null;
    }

}
