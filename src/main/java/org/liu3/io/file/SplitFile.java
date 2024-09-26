package org.liu3.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 将一个大文件拆分成几个小文件
 * 按线程数拆分,可能会出现乱码,
 * 但是用MergeFile合并后就没有乱码了
 * @Author: liutianshuo
 * @Date: 2020/11/20
 */
public class SplitFile {

    public static void main(String[] args) {
        SplitFile sf = new SplitFile();
        sf.read();

    }

    CountDownLatch latch;
    int taskCount = 4;
    int size = 36;
    ArrayList<Thread> taskList;
    String sourcePath;
    String targetPath;
    File sourceFile;

    public SplitFile() {
        latch = new CountDownLatch(taskCount);
        taskList = new ArrayList<>(size);
        sourcePath = "d:/java学习.txt";
        targetPath = "d:/2.txt";
        sourceFile = new File(sourcePath);
    }

    public void read() {

        for (int i = 0; i < taskCount; i++) {
            taskList.add(new Thread(new ReadTask(i, size)));
        }

        taskList.forEach(t -> t.start());


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

        public ReadTask(int num, int size) {
            this.num = num;
            this.size = size;
        }

        private void skipBytes(RandomAccessFile raf, long jump) throws IOException {
            if (jump <= Integer.MAX_VALUE) {
                raf.skipBytes((int) jump);
            } else {
                int skip = 0;
                do {
                    skip = raf.skipBytes(Integer.MAX_VALUE);
                } while (skip > 0);
            }

        }
        private  String getTaskFilePath(){
            return sourceFile.getParent()+num+"+"+sourceFile.getName() + ".bak";
        }

        @Override
        public void run() {

            long length = sourceFile.length();
//            long pageSize = (((length/size)-1)/taskCount+1)*size;


            long pageSize = length/taskCount;
            long mod = pageSize%3;
            pageSize +=mod;

            String taskFilePath = getTaskFilePath();

            try {
                byte[] bs = new byte[size];
                RandomAccessFile raf = new RandomAccessFile(sourceFile, "rw");
                skipBytes(raf, pageSize * num);

                System.out.println("task "+num+" length="+length+" pageSize="+pageSize+" jump="+(pageSize * num));

                RandomAccessFile writer = new RandomAccessFile(taskFilePath, "rw");

                long temp = pageSize;
                int res = -1;
                String s = null;
                long count = 0;

                do {
//                    s = raf.readLine();
//                    if(s == null){
//                        break;
//                    }
//                    s = new String(s.getBytes("ISO-8859-1"),"utf-8")+"\r\n";
//                    writer.writeBytes(s+"\r\n");

                    res = raf.read(bs);
                    if (res <= 0) {
                        break;
                    }
                    count += res;
                    writer.write(bs);

                } while (count < pageSize);

                raf.close();
                writer.close();

//            byte[] bs = new byte[size];
//            ByteBuffer byteBuf = ByteBuffer.allocate(size);
//
//
//
//                FileChannel channel = new RandomAccessFile(sourceFile, "r").getChannel();
//
//                while (channel.read(byteBuf) != -1) {
//
//                    int size = byteBuf.position();
//                    byteBuf.rewind();   //重置position
//                    byteBuf.get(bs); // 把文件当字符串处理，直接打印做为一个例子。
//                    System.out.print(new String(bs, 0, size));
//                    byteBuf.clear();
//                }
//
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

}
