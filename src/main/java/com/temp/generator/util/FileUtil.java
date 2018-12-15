package com.temp.generator.util;

import com.temp.generator.consts.CharacterConst;
import com.temp.permission.util.ConsoleUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 向一个文件中写入数据
     * @param str 要写入的内容
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return true-文件写入成功
     */
    public static boolean fileWrite(
            String str,
            String filePath,
            String fileName,
            String fileType
    ) {
        File directory = new File(filePath);
        if (directory.exists()) {
            System.out.println("存在文件夹" + filePath);
        } else {
            ConsoleUtil.formatPrint(filePath);
            String[] fileArr = filePath.split("/");
            File dir;
            List<String> list = new ArrayList<>();
            ConsoleUtil.formatPrint(fileArr);
            for (int i = 1; i < fileArr.length; i++) {
                ConsoleUtil.formatPrint(fileArr);
                ConsoleUtil.formatPrint(fileArr[i]);
                list.add(fileArr[i]);
                String path = fileArr[0] + StringUtil.join(list.toArray(), "/");
                dir = new File(path);
                ConsoleUtil.formatPrint(path);
                if (!dir.exists()) {
                    System.out.println(path);
                    dir.mkdir();
                }
            }
        }

        File file;
        if (fileType.isEmpty()) {
            file = new File(filePath + CharacterConst.CHARACTER_BACKSLASH + fileName);
        } else {
            file = new File(filePath + CharacterConst.CHARACTER_BACKSLASH + fileName + CharacterConst.CHARACTER_SPOT + fileType);
        }

        if (file.exists()) {
            System.out.println("存在文件" + file.getPath() + file.getName());
        } else {
            try {
                file.createNewFile(); // 文件的创建，注意与文件夹创建的区别
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }

        // 下面是向文件fileName里面写数据
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(str);
            fileWriter.close(); // 关闭数据流
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String fileRead(String fileName) {
        String read = "";
        // 一般先创建file对象
        FileInputStream fileInput = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] buffer = new byte[1024];
            fileInput = new FileInputStream(file);
            int byteread = 0;
            // byteread表示一次读取到buffers中的数量。
            while ((byteread = fileInput.read(buffer)) != -1) {
                read += new String(buffer, 0, byteread);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {

            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return read;
    }

    public static String fileRead1(String filePath) {
        File file = new File(filePath);
        Reader reader = null;
        String str = "";
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(filePath));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            str += tempchars.toString();
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
}
