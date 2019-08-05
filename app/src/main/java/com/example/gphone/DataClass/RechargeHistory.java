package com.example.gphone.DataClass;

public class RechargeHistory {

    private String credited, recharged, regnum;
    long millisec;
    Integer amount;


    public RechargeHistory() {
    }

    public RechargeHistory(String credited, String recharged, Integer amount, String registernum, long millisec) {

        this.credited = credited;
        this.recharged = recharged;
        this.regnum = registernum;
        this.millisec = millisec;
        this.amount = amount;
    }

    public String getCredited() {
        return credited;
    }

    public long getMillisec() {
        return millisec;
    }

    public String getRecharged() {
        return recharged;
    }

    public String getRegisternum() {
        return regnum;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCredited(String credited) {
        this.credited = credited;
    }

    public void setMillisec(long millisec) {
        this.millisec = millisec;
    }

    public void setRecharged(String recharged) {
        this.recharged = recharged;
    }
}
