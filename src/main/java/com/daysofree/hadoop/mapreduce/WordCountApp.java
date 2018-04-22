package com.daysofree.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Hello world!
 */
public class WordCountApp {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        System.setProperty("HADOOP_USER_NAME", "root");

        Configuration configuration = new Configuration();
        configuration.addResource("classpath:core-site.xml");
        configuration.addResource("classpath:hdfs-site.xml");

        // 准备清理已存在的输出目录
        Path outputPath = new Path(args[1]);
        FileSystem fileSystem = FileSystem.get(configuration);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
            System.out.println("删除已存在的结果");
        }

        Job job = Job.getInstance(configuration, "MyWordCount");

        // 设置job处理类
        job.setJarByClass(WordCountApp.class);
        // 设置输入文件
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        // 设置Mapper相关参数
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 设置reducer相关参数
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 设置输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        /**
         * @param value   行号
         * @param key     每一行的字符串
         * @param context 程序上下文
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable value, Text key, Context context) throws IOException, InterruptedException {
            LongWritable one = new LongWritable(1);
            // 接收到的每一行数据，按照空格拆分单词
            String[] words = key.toString().split(" ");
            for (String word : words) {
                // 输出到上线文
                context.write(new Text(word), one);
            }
        }
    }

    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        /**
         * @param key     拆分的单词
         * @param values  单个单词的个数
         * @param context 程序上下文
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (LongWritable value : values) {
                // 统计每个单词出现的次数
                sum += value.get();
            }
            // 输出到设置的输出路径
            context.write(key, new LongWritable(sum));
        }
    }

}
