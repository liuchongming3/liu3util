package org.liu3.io.file;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @Author: liutianshuo
 * @Date: 2020/11/24
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws Exception {

    }

    /** 向指定文件里填充空数据 */
    public void fillFile(String file, long size) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(size);
        raf.close();
    }
}
