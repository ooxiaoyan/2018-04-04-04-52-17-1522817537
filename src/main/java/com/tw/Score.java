package com.tw;

/**
 * Created by 筱湮 on 2018/4/15 0015.
 */
public class Score {
    private double math;
    private double chinese;
    private double english;
    private double programming;
    private double average;
    private double summary;

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getChinese() {
        return chinese;
    }

    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public double getProgramming() {
        return programming;
    }

    public void setProgramming(double programming) {
        this.programming = programming;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public void calculateSummary() {
        this.summary = getMath() + getChinese() + getEnglish() + getProgramming();
    }

    public void calculateAverage() {
        this.average = getSummary() / 4.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (Double.compare(score.math, math) != 0) return false;
        if (Double.compare(score.chinese, chinese) != 0) return false;
        if (Double.compare(score.english, english) != 0) return false;
        if (Double.compare(score.programming, programming) != 0) return false;
        if (Double.compare(score.average, average) != 0) return false;
        return Double.compare(score.summary, summary) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(math);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(chinese);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(english);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(programming);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(summary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
