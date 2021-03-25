package org.liu3.io.file;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * 将4个临时文件的内容依次写入一个文件里
 * 这4个临时文件是SplitFile拆分出的,可能有乱码,
 * 但是合并后就没有乱码了
 * @Author: liutianshuo
 * @Date: 2020/11/24
 */
public class MergeFile {

    public static void main(String[] args) throws Exception {
        MergeFile mf = new MergeFile();
        mf.write();
    }
    String[] files;
    String targetFile;
    public MergeFile(){
        files = new String[]{"d:/0+java学习.txt.bak","d:/1+java学习.txt.bak",
                "d:/2+java学习.txt.bak","d:/3+java学习.txt.bak"};
        targetFile = "d:/target.txt";
    }


    public void write() throws Exception {


        RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
        byte[] bs = new byte[1024];

        for(String s : files){
            writerChild(s, raf);
        }
        raf.close();
        System.out.println("finsh");
    }

    private void writerChild(String childFile, RandomAccessFile raf) throws Exception {
        byte[] bs = new byte[1024];
        RandomAccessFile craf = new RandomAccessFile(childFile, "rw");

        int c = 0;

        while (true){
            c = craf.read(bs);
            System.out.println(c);
            if(c <= 0){
                break;
            }
            raf.write(bs);
        }
        craf.close();
    }

}
