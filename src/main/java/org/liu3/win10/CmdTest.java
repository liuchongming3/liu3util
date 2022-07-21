package org.liu3.win10;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author: liutianshuo
 * @Date: 2022/2/23
 */
public class CmdTest {

    private static Logger logger = LoggerFactory.getLogger(CmdTest.class);

    public static void main(String[] args) throws Exception {
        String a = "13";
        int b = 0;
        switch (a){
            case "1":
                b = 1;
                break;
            case "2":
                b = 2;
                break;
        }

//        var aa = "123";
//        folderCopy("d:/1.txt","d:/3.txt");
//        synCmd();
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec("ping www.baidu.com");

//        FileOutputStream os = new FileOutputStream(new File("d:/1.txt"));
//        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR",os);
//        errorGobbler.start();
//        StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT",os);
//        outGobbler.start();
//        p.waitFor();
        System.out.println(StringUtils.leftPad("1234", 13, '0'));



    }

    /**
     * 一般cmd都是异步运行的方式，也就是在执行命令之后会不等exec执行完就执行下一条语句，
     * 为了实现同步结果，或者为了获取返回的结果
     */
    public static void synCmd(){
        long start = System.currentTimeMillis();
        String srcPath = "C:/Users/liqiang/Desktop/ww/tt.docx";
        String desPath = "C:/Users/liqiang/Desktop/ww";

        String command = "";
        synexec("notepad");
        long end = System.currentTimeMillis();
        logger.debug("用时:{} ms", end - start);
    }

    public static boolean synexec(String command) {
        // Process可以控制该子进程的执行或获取该子进程的信息
        Process process;
        try {
            logger.debug("exec cmd : {}", command);
            /*
             * exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，
             * 并返回与该子进程对应的Process对象实例。
             */
            process = Runtime.getRuntime().exec(command);

            //下面两个可以获取输入输出流
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();

        } catch (IOException e) {
            logger.error(" exec {} error", command, e);
            return false;
        }

        int exitStatus = 0;
        try {
            // 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值
            exitStatus = process.waitFor();
            // 第二种接受返回值的方法
            int i = process.exitValue(); // 接收执行完毕的返回值
            logger.debug("i----" + i);
        } catch (InterruptedException e) {
            logger.error("InterruptedException  exec {}", command, e);
            return false;
        }

        if (exitStatus != 0) {
            logger.error("exec cmd exitStatus {}", exitStatus);
        } else {
            logger.debug("exec cmd exitStatus {}", exitStatus);
        }

        // 销毁子进程
        process.destroy();

        return true;
    }

    public static void folderCopy(String fromPath, String toPath) throws IOException {
        String strCmd = "cmd /c xcopy /Y " + fromPath + " " + toPath;
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("d:/cmd.exe echo 123 > d:/1.txt && echo 321 d:/2.txt");
    }

    public static void memory(){
        long totalMemory = Runtime.getRuntime().totalMemory();//总内存
        long freeMemory = Runtime.getRuntime().freeMemory();//剩余内存
        long maxMemory = Runtime.getRuntime().maxMemory();//最大内存
        System.out.println(totalMemory/1024/1024+"MB");
        System.out.println(freeMemory/1024/1024+"MB");
        System.out.println(maxMemory/1024/1024+"MB");
    }

    static class StreamGobbler extends Thread {
        private InputStream is;
        private String type;
        private OutputStream os;

        StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        public void run() {
            InputStreamReader isr = null;
            BufferedReader br = null;
            PrintWriter pw = null;
            try {
                if (os != null)
                    pw = new PrintWriter(os);

                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (pw != null)
                        pw.println(line);
                    System.out.println(type + ">" + line);
                }
                if (pw != null) pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(pw!=null) pw.close();
                    if(br!=null) br.close();
                    if(isr!=null) isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
