package com.jf2mc1.a015004rexerciseideas.model;

public class Workout {

    private String type;
    private String desc;
    private int done;
    private boolean isLiked;

    public Workout(String type, String desc) {
        this.type = type;
        this.desc = desc;
        done = 0;
        isLiked = false;
    }

    public int getDone() {
        return done;
    }

    public void increaseDone(int increaseBy) {
        done += increaseBy;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
