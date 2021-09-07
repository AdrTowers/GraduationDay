package com.graduation.pointsystem;

import java.util.ArrayList;
import java.util.List;

public class QuestionTemplate {
    private int response_code;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<QuestionDetail> getResults() {
        return results;
    }

    public void setResults(List<QuestionDetail> results) {
        this.results = results;
    }

    private List<QuestionDetail> results =new ArrayList<>();
}
