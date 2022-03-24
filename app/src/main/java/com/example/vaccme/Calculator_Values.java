package com.example.vaccme;

public class Calculator_Values {
    private int chekpoint;
    private int hospit;
    private int allchek = 0;

    public Calculator_Values(int chekpoint, int hospit) {
        this.chekpoint = chekpoint;
        this.hospit = hospit;
    }

    public void setChekpoint(int chekpoint) {
        this.chekpoint = chekpoint;
    }

    public int getChekpoint() {
        return this.chekpoint;
    }

    public void setHospit(int hospit) {
        this.hospit = hospit;
    }

    public int getHospit() {
        return this.hospit;
    }

    public void setallchek(int allchek) {
        this.allchek = allchek;
    }

    public int getAllchek() {
        return this.allchek;
    }

    public int f1(int allchek) {
        allchek += hospit;
        return allchek;
    }
}
