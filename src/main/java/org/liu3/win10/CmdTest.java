package org.liu3.win10;

import java.io.IOException;

/**
 * @Author: liutianshuo
 * @Date: 2022/2/23
 */
public class CmdTest {

    public static void main(String[] args) throws IOException {
        folderCopy("d:/1.txt","d:/3.txt");
    }

    public static void folderCopy(String fromPath, String toPath) throws IOException {
        String strCmd = "cmd /c xcopy /Y " + fromPath + " " + toPath;
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("d:/cmd.exe start d:/1.txt");
    }

}
