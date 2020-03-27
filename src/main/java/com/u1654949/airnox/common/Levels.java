package com.u1654949.airnox.common;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Levels {

    private Integer alarm_level;
    private Integer warning_level;

    public Levels(){
        // no-op
    }

    public Levels(int alarm_level, int warning_level){
        this.alarm_level = alarm_level;
        this.warning_level = warning_level;
    }

    public Integer getAlarmLevel(){
        return alarm_level;
    }

    public Integer getWarningLevel(){
        return warning_level;
    }

    @JsonSetter("alarm_level")
    public void setAlarmLevel(int alarm_level){
        this.alarm_level = alarm_level;
    }

    @JsonSetter("warning_level")
    public void setWarningLevel(int warning_level){
        this.warning_level = warning_level;
    }

} 