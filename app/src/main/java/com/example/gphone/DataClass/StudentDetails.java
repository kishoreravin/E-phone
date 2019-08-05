package com.example.gphone.DataClass;

public class StudentDetails {
    String num1, num2, num3, num4;
    Integer balance;

    public StudentDetails() {
    }

    public StudentDetails(String num1, String num2, String num3, String num4, Integer balance) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.balance = balance;
    }

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public String getNum3() {
        return num3;
    }

    public String getNum4() {
        return num4;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public void setNum4(String num4) {
        this.num4 = num4;
    }
}
