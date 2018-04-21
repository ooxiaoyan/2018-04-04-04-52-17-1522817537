package com.tw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 筱湮 on 2018/4/13 0013.
 * 处理一些核心的计算
 */
public class Service {

    public Student addStudentInfoService(String input) {
        boolean flag = addStudentInfoValidate(input);
        //验证输入 如果正确返回student 否则返回null
        if (flag) {
            return studentStringToObject(input);
        } else {
            return null;
        }
    }

    public boolean addStudentInfoValidate(String input) {
        String regex = "^([\u4e00-\u9fa5]+|([a-zA-Z]+\\s?)+\\s?),(\\s?TWA\\d{8}\\s?)," +
                "((math:\\d{2}|(chinese:\\d{2})|(programing:\\d{2})|(english:\\d{2})),){3}" +
                "math:\\d{2}|(chinese:\\d{2})|(programing:\\d{2})|(english:\\d{2})$";
        boolean result = regexMatching(regex, input);
        return result;
    }

    public boolean regexMatching(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find();
    }

    public Student studentStringToObject(String studentString) {
        String[] studentInfoArray = studentString.split(",");
        Student student = new Student();
        student.setName(studentInfoArray[0]);
        student.setId(studentInfoArray[1]);
        Score score = new Score();
        for (int i = 2; i < studentInfoArray.length; i++) {
            String[] courseArray = studentInfoArray[i].split(":");
            if (courseArray[0].equals("math")) {
                score.setMath(Double.parseDouble(courseArray[1]));
            } else if (courseArray[0].equals("chinese")) {
                score.setChinese(Double.parseDouble(courseArray[1]));
            } else if (courseArray[0].equals("english")) {
                score.setEnglish(Double.parseDouble(courseArray[1]));
            } else if (courseArray[0].equals("programming")) {
                score.setProgramming(Double.parseDouble(courseArray[1]));
            } else {
                return null;
            }
        }
        //这一步主要是为了弥补正则表达式不能匹配输入同样的科目也验证正确的问题，是笔者的技术问题，待解决--、、
        if (score.getProgramming() == 0 || score.getEnglish() == 0 || score.getChinese() == 0 || score.getMath() == 0) {
            return null;
        }
        student.setScore(score);
        return student;
    }

    public void addStudentInfo(Student student, List<Student> students) {
        students.add(student);
    }

    public boolean generateScoreSheetValidate(String studentsId) {
        String regex = "^((TWA\\d{8}),)*((TWA\\d{8}),?)$";
        boolean result = regexMatching(regex, studentsId);
        return result;
    }

    public boolean generateScoreSheetService(String studentsId, List<Student> students) {
        if (students.size() == 0) {
            return false;
        }
        //查找学生成绩
        List<Student> studentList = findStudents(studentsId, students);
        if (studentList.size() == 0) {
            return false;
        }
        //计算每个学生的总分和平均分
        calculateSummaryAndAverage(studentList);
        //计算全班总分平均数
        double totalSummaryAverage = averageOfTotalSummary(studentList);
        //计算全班总分中位数
        double totalSummaryMedian = medianOfTotalSummary(studentList);
        //打印成绩单
        printScoreSheet(studentList, totalSummaryAverage, totalSummaryMedian);
        return true;
    }

    public List<Student> findStudents(String studentsId, List<Student> students) {
        String[] studentIdArray = studentsId.split(",");
        List<String> studentIdList = Arrays.stream(studentIdArray).distinct().collect(Collectors.toList());
        List<Student> resultList = new ArrayList<>();
        for (int i = 0; i < studentIdList.size(); i++) {
            for (int j = 0; j < students.size(); j++) {
                if (studentIdList.get(i).equals(students.get(j).getId())) {
                    resultList.add(students.get(i));
                    break;
                }
            }
        }
        return resultList;
    }

    public void calculateSummaryAndAverage(List<Student> students) {
        for (Student student : students) {
            student.getScore().calculateSummary();
            student.getScore().calculateAverage();
        }
    }

    public double averageOfTotalSummary(List<Student> students) {
        double sum = 0.0;
        for (Student student : students) {
            sum = sum + student.getScore().getSummary();
        }
        return sum / students.size();
    }

    public double medianOfTotalSummary(List<Student> students) {
        List<Student> sortStudents = students.stream().sorted().collect(Collectors.toList());
        int size = sortStudents.size();
        if (size % 2 == 0) {
            return (sortStudents.get(size/2 - 1).getScore().getSummary() + sortStudents.get(size/2).getScore().getSummary()) / 2.0;
        } else {
            return sortStudents.get(size/2).getScore().getSummary();
        }
    }

    public void printScoreSheet(List<Student> students, double totalSummaryAverage, double totalSummaryMedian) {
        System.out.print(
                "\n成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n");
        students.forEach(System.out::print);
        System.out.print("========================\n" +
                "全班总分平均数：" +
                totalSummaryAverage +
                "\n全班总分中位数：" +
                totalSummaryMedian + "\n");
    }

}
