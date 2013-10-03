package com.xinlab.blueapple.contenttool.perf;

public class Node007 {
    String type;
    String id;
    long startTime;
    int duration;
    String extra;

    public Node007() {
        super();
    }

    public Node007(String type, String id, long startTime, int duration) {
        super();
        this.type = type;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }
    
    public Node007(String type, String id, long startTime, int duration, String extra) {
        super();
        this.type = type;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.extra = extra;
    }

    public String toString() {
        return type+","+id+","+startTime+","+duration+","+(extra==null?"":extra);
    }
}
