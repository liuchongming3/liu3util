package org.liu3.io.file;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: liutianshuo
 * @Date: 2020/11/20
 */
public class SplitFile {

    CountDownLatch latch;
    int taskCount = 4;
    int size = 32;
    ArrayList<Thread> taskList;
    String sourceFile;
    String targetFile;

    public SplitFile(){
        latch = new CountDownLatch(taskCount);
        taskList = new ArrayList<>(size);
        sourceFile = "d:/1.txt";
        targetFile = "d:/2.txt";
    }

    public void read(){

        for(int i=1; i<=taskCount;i++){
            taskList.add(new Thread(new ReadTask(i, size)));
        }

        taskList.forEach(t-> t.start());


        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("文件读取完毕");
    }

    class ReadTask implements Runnable {

        int num;
        int size;
        public ReadTask(int num, int size){

            File f = new File(sourceFile);

        }

        @Override
        public void run() {

            latch.countDown();
        }
    }

}
