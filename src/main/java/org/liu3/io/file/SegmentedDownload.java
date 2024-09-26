package org.liu3.io.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 使用多个线程从源文件分段读取数据,分段写入目标文件
 * @Author: liutianshuo
 * @Date: 2020/11/24
 */
public class SegmentedDownload {

    File src;
    File desc;

    int taskCount;
    CountDownLatch latch;

    public static void main(String[] args) throws Exception {
        SegmentedDownload sd = new SegmentedDownload(new File("d:/java学习.txt"), new File("d:/java学习2.txt"), 4);
        sd.download();

    }

    public SegmentedDownload(File src, File desc, int taskCount) {
        this.src = src;
        this.desc = desc;
        this.taskCount = taskCount;
        latch = new CountDownLatch(taskCount);
    }

    public void download() throws Exception {

        long length = src.length();

        if(desc.length() == 0){
            RandomAccessFile raf = new RandomAccessFile(desc, "rw");
            raf.setLength(length);
            raf.close();
        }

        ArrayList<Downlod> list = new ArrayList<>(taskCount);

        long pageSize = (length+(length%taskCount))/taskCount;

        for (int i=0;i<taskCount;i++){
            Downlod dd = new Downlod(i, i*pageSize, pageSize);
            list.add(dd);
        }

        list.forEach(t -> t.start());

        this.latch.await();
        System.out.println("finish");
    }

    public static void skipBytes(RandomAccessFile raf, long jump) throws IOException {

        if (jump <= Integer.MAX_VALUE) {
            raf.skipBytes((int) jump);
        } else {
            int skip = 0;
            int sub = Integer.MAX_VALUE;

            while (sub >= Integer.MAX_VALUE){

                raf.skipBytes(sub);
                jump = jump - Integer.MAX_VALUE;
                if(jump <= Integer.MAX_VALUE){
                    sub = (int)(jump);
                }
            }

        }

    }

    class Downlod extends Thread {

        int num;
        long start;
        long sizePage;

        Downlod(int num, long start, long sizePage){
            this.num=num;
            this.start =start;
            this.sizePage = sizePage;
        }

        @Override
        public void run() {

            byte[] bs = new byte[64];

            try (RandomAccessFile raf1 = new RandomAccessFile(src, "rw");
                 RandomAccessFile raf2 = new RandomAccessFile(desc, "rw")) {


                skipBytes(raf1, start);
                skipBytes(raf2, start);
                long sum = 0;
                long maxLen = sizePage;
                int res = 0;
                while ((res = raf1.read(bs)) > -1){
                    sum = sum+res;
                    if(sum > maxLen){
                        int len = (int)(sizePage-sum)+res;
                        raf2.write(bs,0, len);
                        break;
                    }else if(sum < maxLen){
                        raf2.write(bs);
                    }else {
                        raf2.write(bs);
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

}
