package com.example.gphone.DataClass;

public class WardenDetails {
    private String name, password, schoolname;
    private long amountcredited, amountrecharged;

    public WardenDetails() {
    }

    public WardenDetails(String name, String password, String schoolname, long amountCredited, long amountRecharged) {
        this.name = name;
        this.password = password;
        this.amountcredited = amountCredited;
        this.amountrecharged = amountRecharged;
        this.schoolname = schoolname;
    }

    public long getAmountCredited() {
        return amountcredited;
    }

    public long getAmountRecharged() {
        return amountrecharged;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setAmountCredited(long amountcredited) {
        this.amountcredited = amountcredited;
    }

    public void setAmountRecharged(long amountrecharged) {
        this.amountrecharged = amountrecharged;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }
}
