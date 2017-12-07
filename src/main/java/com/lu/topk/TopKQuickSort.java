package com.lu.topk;

import com.lu.sort.QuickSortUtils;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 基于快速排序和MapReduce的TopK，解决了出现重复值会覆盖的问题
 */
public class TopKQuickSort {
    static class TopKQuickSortMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] strings = value.toString().split("\t");
            for (String string : strings) {
                String[] line = string.split(" ");// 18.13.253.64    10001
                String ip = line[0];// 18.13.253.64
                String num = line[line.length - 1];// 10001
                context.write(new Text(ip), new Text(num));
            }
        }
    }

    static class TopKQuickSortReducer extends Reducer<Text, Text, Text, Text> {
        ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
//                System.out.println(key + "\t" + value);
                HashMap<String, Integer> item = new HashMap<String, Integer>();
                item.put(key.toString(), Integer.parseInt(value.toString()));
                list.add(item);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
//            for (HashMap<String, Integer> item : list) {
//                for (String s : item.keySet()) {
//                    System.out.println(s + "\t" + item.get(s));
//                }
//            }
            QuickSortUtils quickSortUtils = new QuickSortUtils();
            quickSortUtils.quickSort(list, 0, list.size() - 1);
            /**
             * 默认从小到大排序
             * TopK问题：
             *  1. 如果取大的Top值：倒序迭代输出对应的n个值
             *  2. 如果取小的Top值：正序迭代输出对应的n个值
             */
//            for (int i = 0; i < 5; i++) {
//                HashMap<String, Integer> item = list.get(i);
//                for (String s : item.keySet()) {
//                    context.write(new Text(s), new Text(item.get(s).toString()));
//                }
//            }
            for (int i = list.size() - 1; i >= list.size() - 5; i--) {
                HashMap<String, Integer> item = list.get(i);
                for (String s : item.keySet()) {
                    context.write(new Text(s), new Text(item.get(s).toString()));
                }
            }
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {
        if (args.length != 2) {
            System.out.println("必须有两个参数");
            System.exit(-1);
        }

        Configuration configuration = new Configuration();
        FileSystem hdfs = FileSystem.get(new URI(args[1]), configuration);
        if (hdfs.exists(new Path(args[1]))) {
            hdfs.delete(new Path(args[1]), true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(TopKQuickSort.class);
        job.setJobName("TopKQuickSort");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(TopKQuickSortMapper.class);
        job.setReducerClass(TopKQuickSortReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
