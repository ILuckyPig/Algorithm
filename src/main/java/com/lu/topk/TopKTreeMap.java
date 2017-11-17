package com.lu.topk;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

/**
 * 基于红黑树和MapReduce的TopK
 */
public class TopKTreeMap {
    /**
     * BUG：
     *  构建TreeMap时，需要用访问次数作为键，如果不同ip的访问次数相同，会覆盖之前的ip。
     */
    static class TopKMapper extends Mapper<Object, Text, IntWritable, Text> {
        private static int K = 3;
        private static TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] strings = value.toString().split("\t");
            for (String string : strings) {
                String[] lines = string.split(" ");
//                System.out.println(lines[0] + " __" + lines[lines.length - 1]);
                String ip = lines[0];
                int num = Integer.parseInt(lines[lines.length - 1]);
                treeMap.put(num, ip);
                if (treeMap.size() > K) {
                    treeMap.remove(treeMap.firstKey());
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Integer integer : treeMap.keySet()) {
                context.write(new IntWritable(integer), new Text(treeMap.get(integer)));
            }
        }
    }

    static class TopKReduce extends Reducer<IntWritable, Text, Text, IntWritable> {
        private static int K = 10;
        private static TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        @Override
        protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                treeMap.put(key.get(), value.toString());
                if (treeMap.size() > K) {
                    treeMap.remove(treeMap.firstKey());
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Integer integer : treeMap.keySet()) {
                context.write(new Text(treeMap.get(integer)), new IntWritable(integer));
            }
        }
    }
    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {
        if (args.length != 2) {
            System.out.println("Must has two params");
            System.exit(-1);
        }
        Configuration configuration = new Configuration();
        FileSystem hdfs = FileSystem.get(new URI(args[1]), configuration);
        if (hdfs.exists(new Path(args[1]))) {
            hdfs.delete(new Path(args[1]), true);
        }

        Job job = Job.getInstance(configuration);
        job.setJarByClass(TopKTreeMap.class);
        job.setJobName("TopK TreeMap");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(TopKMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(TopKReduce.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
