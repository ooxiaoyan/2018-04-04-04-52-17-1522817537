package com.tw;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Command command;
    private Service service;

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
        command = new Command();
        service = new Service();
    }

    @Test
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }

    @Test
    public void testMockClass() throws Exception {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        String value = "first";
        when(mockedList.get(0)).thenReturn(value);

        assertEquals(mockedList.get(0), value);

    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void testMainMenu() {
        Library.mainMenu();
        assertThat(systemOut(),is("\n1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）："));
    }

    @Test
    public void testSecondMenu() {
        String rightStudentInfo = oneRecordString();
        String expectedResult = "学生张三的成绩被添加";
        List<Student> studentList = new ArrayList<>();
        command.secondMenu("1", rightStudentInfo, studentList);
        assertThat(systemOut().endsWith(expectedResult),is(true));

        String inputFalse1 = "张三,TWA20160101,";
        String expectedResult1 = "请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：";
        command.secondMenu("1", inputFalse1, studentList);
        assertThat(systemOut().endsWith(expectedResult1),is(true));
    }

    @Test
    public void testStudentStringToObject() {
        Student result = service.studentStringToObject(oneRecordString());
        Student student = oneRecord();
        assertEquals(student, result);
    }

    public String oneRecordString() {
        return "张三,TWA20180101,math:78,english:89,chinese:90,programming:90";
    }

    public Student oneRecord() {
        return service.studentStringToObject(oneRecordString());
    }

    public List<Student> threeRecords() {
        String[] studentsString3 = {
                "张三,TWA20180101,math:78,english:79,chinese:80,programming:79",
                "李四,TWA20180102,math:89,english:90,chinese:91,programming:90",
                "王五,TWA20180103,math:80,english:81,chinese:82,programming:81"};
        List<Student> students = new ArrayList<>();
        for (String s : studentsString3) {
            Student student = service.studentStringToObject(s);
            students.add(student);
        }
        return students;
    }

    public List<Student> fourRecords() {
        String[] studentsString4 = {
                "张三,TWA20180101,math:78,english:79,chinese:80,programming:79",
                "李四,TWA20180102,math:89,english:90,chinese:91,programming:90",
                "王五,TWA20180103,math:80,english:81,chinese:82,programming:81",
                "王六,TWA20180104,math:78,english:80,chinese:82,programming:80"};
        List<Student> students = new ArrayList<>();
        for (String s : studentsString4) {
            Student student = service.studentStringToObject(s);
            students.add(student);
        }
        return students;
    }

    @Test
    public void testAverageOfTotalSummary() {
        List<Student> students = threeRecords();
        service.calculateSummaryAndAverage(students);
        double result = service.averageOfTotalSummary(students);
        assertThat(result,is(1000/3.0));
    }

    @Test
    public void testMedianOfTotalSummary() {
        List<Student> students = threeRecords();
        service.calculateSummaryAndAverage(students);
        double result3 = service.medianOfTotalSummary(students);
        assertThat(result3,is(324.0));

        students.clear();
        students = fourRecords();
        service.calculateSummaryAndAverage(students);
        double result4 = service.medianOfTotalSummary(students);
        assertThat(result4,is(322.0));
    }

    @Test
    public void testGenerateScoreSheetValidate() {
        String studentsId = "TWA20180101,TWA20180102,TWA20180103,TWA20180101,TWA20180102";
        boolean result = service.generateScoreSheetValidate(studentsId);
        assertTrue(result);
        studentsId = "TWA20180101,";
        result = service.generateScoreSheetValidate(studentsId);
        assertTrue(result);
        studentsId = "TWA20180101,TWA20180102,";
        result = service.generateScoreSheetValidate(studentsId);
        assertTrue(result);
        studentsId = "TWA20180101,TWA2018010,";
        result = service.generateScoreSheetValidate(studentsId);
        assertTrue(!result);
        studentsId = "TWA20180101,TWA2018010$,";
        result = service.generateScoreSheetValidate(studentsId);
        assertTrue(!result);
        studentsId = "";
        result = service.generateScoreSheetValidate(studentsId);
        assertTrue(!result);
    }

    @Test
    public void testGenerateScoreSheetServiceWithThreeRecords() {
        String studentsId = "TWA20180101,TWA20180102,TWA20180103";
        service.generateScoreSheetService(studentsId, threeRecords());
        String result = "\n成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|78.0|80.0|79.0|79.0|79.0|316.0\n" +
                "李四|89.0|91.0|90.0|90.0|90.0|360.0\n" +
                "王五|80.0|82.0|81.0|81.0|81.0|324.0\n" +
                "========================\n" +
                "全班总分平均数："+ 1000/3.0 +"\n" +
                "全班总分中位数：324.0\n";
        assertThat(systemOut(), is(result));
    }

    @Test
    public void testGenerateScoreSheetServicewithOneRecord() {
        String studentsId = "TWA20180101,";
        service.generateScoreSheetService(studentsId, threeRecords());
        String result = "\n成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|78.0|80.0|79.0|79.0|79.0|316.0\n" +
                "========================\n" +
                "全班总分平均数：316.0\n" +
                "全班总分中位数：316.0\n";
        assertThat(systemOut(), is(result));
    }

    @Test
    public void testGenerateScoreSheetServiceWithRepeatedId() {
        String studentsId = "TWA20180101,TWA20180102,TWA20180103,TWA20180101,TWA20180102";
        service.generateScoreSheetService(studentsId, threeRecords());
        String result = "\n成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|78.0|80.0|79.0|79.0|79.0|316.0\n" +
                "李四|89.0|91.0|90.0|90.0|90.0|360.0\n" +
                "王五|80.0|82.0|81.0|81.0|81.0|324.0\n" +
                "========================\n" +
                "全班总分平均数："+ 1000/3.0 +"\n" +
                "全班总分中位数：324.0\n";
        assertThat(systemOut(), is(result));
    }

    @Test
    public void testAddStudentInfoService() {
        Student student = oneRecord();
        String input = oneRecordString();
        Student result = service.addStudentInfoService(input);
        assertEquals(student, result);
    }

    @Test
    public void testAddStudentInfoValidate() {
        String input = oneRecordString();
        boolean result = service.addStudentInfoValidate(input);
        assertThat(result, is(true));
    }
}
