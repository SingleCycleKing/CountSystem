package com.unique.countsystem.database.model;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public enum absenceType{
    /**
     * normal state
     */
    NORMAL(0),
    /**
     * vacate
     */
    VACATE(1),
    /**
     * absence for no reason
     */
    ABSENCE(2);

    private absenceType(int category){
        this.category = category;
    }
    @Override
    public String toString(){
        return super.toString()+"("+category+")";
    }

    private int category=0;

    public int toInteger(){
        return category;
    }
}
