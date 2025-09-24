package com.example.quizapp.service;

import com.example.quizapp.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionsService {

    private final Map<Integer, Question> quizMap = new HashMap<>();
    private int currentId = 1;

    public List<Question> loadQuizzes() {
        return new ArrayList<>(quizMap.values());
    }

    public void addQuiz(Question question) {
        question.setId(currentId++);
        quizMap.put(question.getId(), question);
    }

    public void editQuiz(Question updatedQuestion) {
        if (quizMap.containsKey(updatedQuestion.getId())) {
            quizMap.put(updatedQuestion.getId(), updatedQuestion);
        }
    }

    public void deleteQuiz(int id) {
        quizMap.remove(id);
    }

    public Question getQuizById(int id) {
        return quizMap.get(id);
    }
}
