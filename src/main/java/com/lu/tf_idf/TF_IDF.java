package com.lu.tf_idf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TF_IDF {
    static class TF_IDFMapper1 extends Mapper<LongWritable, Text, Text, Text> {
        int sum = 0;
        String fileName = "";
        String word;
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            FileSplit split = (FileSplit) context.getInputSplit();
            String str = split.getPath().toString();
            fileName = str.substring(str.lastIndexOf("/" + 1));

            String[] strings = value.toString().split(" ");
            for (String string : strings) {
                word = fileName;
                word += " ";
                word += string;
                sum++;
                context.write(new Text(word), new Text(1 + ""));
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            context.write(new Text(fileName + " " + "!"), new Text(sum + ""));
        }
    }

    static class TF_IDFCombiner1 extends Reducer<Text, Text, Text, Text> {
        int sum = 0;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int index = key.toString().indexOf(" ");
            if ("!".equals(key.toString().substring(index + 1, index + 2))) {
                for (Text value : values) {
                    sum = Integer.parseInt(value.toString());
                }
                return;
            }
            float count = 0;
            for (Text value : values) {
                count = count + Float.parseFloat(value.toString());
            }
            float tf = count / sum;
//            System.out.println(tf);

            // 交换key中单词和文件名的顺序
            String[] p = key.toString().split(" ");
            String key_to = "";
            key_to += p[1];
            key_to +=  " ";
            key_to += p[0];

            context.write(new Text(key_to), new Text(tf + ""));
        }
    }

    static class TF_IDFReducer1 extends Reducer<Text, Text, Text, Text> {
        int sum = 0;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//            if ("!".equals(key.toString())) {
//                for (IntWritable value : values) {
//                    sum = value.get();
//                }
//                return;
//            }
//            float count = 0;
//            for (IntWritable value : values) {
//                count = count + value.get();
//            }
//            // TF词频
//            float tf = count / sum;
//            context.write(new Text(key + "的词频"), new FloatWritable(tf));
            for (Text value : values) {
                context.write(key, value);
            }
        }
    }

    static class TF_IDFMapper2 extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString().replaceAll("\t", " ");
            // they /172.16.131.8:9000/user/hadoop/input/test.txt   0.007782101
            // 替换tab为空格
            // they /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101
            int index = val.indexOf(" ");
            String word = val.substring(0, index);// 截取单词 they
            String line = val.substring(index + 1);// 截取剩下部分 /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101
            line += " 1";//统计单词在文档库中出现的次数：最小肯定为1 /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101 1
            context.write(new Text(word), new Text(line));// key: they value: /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101 1
        }
    }

    static class TF_IDFReducer2 extends Reducer<Text, Text, Text, Text> {
        int file_count;
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            file_count = context.getNumReduceTasks();// 获取文件总数
            float sum = 0;
            List<String> vals = new ArrayList<String>();
            for (Text value : values) {
                // /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101 1
                int index = value.toString().lastIndexOf(" ");
                sum += Integer.parseInt(value.toString().substring(index + 1));// 统计单词在文档库中出现的次数
                vals.add(value.toString().substring(0, index));// /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101
            }
            double tmp = Math.log10(file_count * 1.0 / (sum + 1.0));
            System.out.println(file_count + "\t" + sum);
            for (int i = 0; i < vals.size(); i++) {
                String val = vals.get(i);// /172.16.131.8:9000/user/hadoop/input/test.txt 0.007782101
                String end = val.substring(val.lastIndexOf(" "));
                float tf = Float.parseFloat(end);// 读取TF
                val += " ";
                val += tf * tmp;
                context.write(key, new Text(val));
            }

        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {

        Configuration conf1 = new Configuration();
        FileSystem hdfs = FileSystem.get(new URI(args[1]), conf1);
        FileStatus[] p = hdfs.listStatus(new Path(args[0]));

        if (hdfs.exists(new Path(args[1]))) {
            hdfs.delete(new Path(args[1]), true);
        }
        if (hdfs.exists(new Path(args[2]))) {
            hdfs.delete(new Path(args[2]), true);
        }

        /*------------------part1-----------------------------*/
        Job job1 = Job.getInstance();

        job1.setJarByClass(TF_IDF.class);
        job1.setJobName("TF_IDF");
        job1.setMapperClass(TF_IDFMapper1.class);
        job1.setCombinerClass(TF_IDFCombiner1.class);
        job1.setReducerClass(TF_IDFReducer1.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        job1.setNumReduceTasks(p.length);

        FileInputFormat.setInputPaths(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));

        job1.waitForCompletion(true);

        /*------------------part2-----------------------------*/
        Configuration conf2 = new Configuration();

        Job job2 = Job.getInstance(conf2);
        job2.setJarByClass(TF_IDF.class);
        job2.setJobName("TF_IDF");
        job2.setMapperClass(TF_IDFMapper2.class);
        job2.setReducerClass(TF_IDFReducer2.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        job2.setNumReduceTasks(p.length);

        FileInputFormat.setInputPaths(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));

        job2.waitForCompletion(true);
    }
}
