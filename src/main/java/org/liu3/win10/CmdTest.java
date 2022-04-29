package org.liu3.win10;

/**
 * @Author: liutianshuo
 * @Date: 2022/2/23
 */
public class CmdTest {

    public static void main(String[] args) {
        folderCopy("d:/1.txt","d:/3.txt");
    }

    public static void folderCopy(String fromPath, String toPath) {
        String strCmd = "cmd /c xcopy /Y " + fromPath + " " + toPath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("d:/cmd.exe start d:/1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
