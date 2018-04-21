package com.tw;

import java.util.List;
import java.util.Scanner;

/**
 * Created by 筱湮 on 2018/4/14 0015.
 * Command负责处理各种具体的用户输入解析
 */
public class Command {
    Service service = new Service();

    public void route(String input, List<Student> students) {
        if ("1".equals(input)) {
            gotoAddStudentInfoCommand();
        } else if ("2".equals(input)) {
            gotoGenerateScoreSheetCommand();
        } else if ("3".equals(input)) {
            exit();
        } else {
            return;
        }
        boolean flag;
        do {
            Scanner in = new Scanner(System.in);
            String secondInput = in.nextLine();
            flag = secondMenu(input, secondInput, students);
        }while (!flag);

    }

    public void gotoAddStudentInfoCommand() {
        System.out.print("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：");
    }
    public void gotoGenerateScoreSheetCommand() {
        System.out.print("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
    }

    public static void exit() {
        System.exit(0);
    }

    public boolean secondMenu(String firstInput, String secondInput, List studentList) {
        boolean result = false;
        if (firstInput.equals("1")) {
            result = addStudentInfoCommand(secondInput, studentList);
        } else if (firstInput.equals("2")) {
            result = scoreSheetPrintCommand(secondInput, studentList);
        }
        return result;
    }

    public boolean addStudentInfoCommand(String studentInfo, List students) {
        Student student = service.addStudentInfoService(studentInfo);
        if (student != null) {
            service.addStudentInfo(student, students);
            System.out.print("学生" + student.getName() + "的成绩被添加");
            return true;
        } else {
            System.out.print("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
            return false;
        }
    }

    public boolean scoreSheetPrintCommand(String studentsId, List<Student> students) {
        boolean generateScoreSheetValidate = service.generateScoreSheetValidate(studentsId);
        if (generateScoreSheetValidate) {
            service.generateScoreSheetService(studentsId, students);
        } else {
            System.out.print("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
        }
        return generateScoreSheetValidate;
    }


}
