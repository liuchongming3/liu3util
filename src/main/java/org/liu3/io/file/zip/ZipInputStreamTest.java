package org.liu3.io.file.zip;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author: liutianshuo
 * @Date: 2021/3/25
 */
public class ZipInputStreamTest {

    public static void main(String[] args) throws IOException {

        //读入zip文件
        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry ze = null;
        try {
            fis = new FileInputStream(new File("d:/123.zip"));
            zis = new ZipInputStream(fis);
            //遍历zip文件里的文件列表
            while ((ze = zis.getNextEntry()) != null) {

                String fileName = ze.getName();
                long size = ze.getSize();

                System.out.println(fileName+"="+size);

                //遍历到哪个被压缩的文件,就可以读取它
                BufferedReader br = new BufferedReader(new InputStreamReader(zis));
                String line;
                //内容不为空，输出
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zis.closeEntry();
            fis.close();
        }
    }

}
