package com.tw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    public boolean someLibraryMethod() {
        return true;
    }

    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        Command command = new Command();
        Scanner in = new Scanner(System.in);
        while (true) {
            mainMenu();
            String firstInput = in.nextLine();
            command.route(firstInput, studentList);
        }
    }

    public static void mainMenu() {
        System.out.print(
                "\n1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：");
    }
}
