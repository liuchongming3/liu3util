package org.liu3.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: liutianshuo
 * @Date: 2020/11/24
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {


        int length=0x8000000;
        length=1024;
        File f = new File("d:/test.txt");
        FileChannel fc = new RandomAccessFile(f, "rw").getChannel();
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, f.length());
        String data="白日依山尽,黄河入海流,欲穷千里目,更上一层楼";
        byte[] bs = new byte[32];

        int cap = out.capacity();
        System.out.println(cap);
        out.get(bs, 0, cap);
//        ByteBuffer bb = out.get(bs);
        System.out.println(new String(bs));

    }
}
