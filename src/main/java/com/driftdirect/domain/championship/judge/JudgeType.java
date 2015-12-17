package com.driftdirect.domain.championship.judge;

/**
 * Created by Paul on 12/17/2015.
 */
public enum JudgeType {
    LINE("Line judge"),
    ANGLE("Angle judge"),
    STYLE("Style judge");

    private String title;

    private JudgeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
