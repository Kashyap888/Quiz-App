package com.example.quizapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Question {
    private int id;
    private String questionText;
    private ArrayList<String> options;
    private String correctAnswer;

    // For Thymeleaf form binding
    private String optionsString;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public ArrayList<String> getOptions() { return options; }
    public void setOptions(ArrayList<String> options) { this.options = options; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getOptionsString() { return optionsString; }
    public void setOptionsString(String optionsString) {
        this.optionsString = optionsString;
        this.options = new ArrayList<>(Arrays.asList(optionsString.split("\\s*,\\s*")));
    }
}
