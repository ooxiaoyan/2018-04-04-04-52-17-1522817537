package com.tw;

/**
 * Created by 筱湮 on 2018/4/15 0015.
 */
public class Student implements Comparable<Student>{
    private String id;
    private String name;
    Score score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return score != null ? score.equals(student.score) : student.score == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Student o) {
        if (this.score.getSummary() > o.getScore().getSummary()) {
            return 1;
        } else if (this.score.getSummary() < o.getScore().getSummary()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return getName() + "|"
                + getScore().getMath() + "|"
                + getScore().getChinese() + "|"
                + getScore().getEnglish() + "|"
                + getScore().getProgramming() + "|"
                + getScore().getAverage() + "|"
                + getScore().getSummary() + "\n";
    }
}
