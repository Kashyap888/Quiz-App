package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.User;
import com.example.quizapp.service.QuestionsService;
import com.example.quizapp.service.QuizUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizUserDetailsService userService;
    private final QuestionsService questionsService;

    public QuizController(QuizUserDetailsService userService, QuestionsService questionsService) {
        this.userService = userService;
        this.questionsService = questionsService;
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/quiz/login";
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        if (principal == null) return "redirect:/quiz/login";

        String username = principal.getName();
        org.springframework.security.core.userdetails.UserDetails userDetails =
                userService.loadUserByUsername(username);

        List<Question> quizzes = questionsService.loadQuizzes();
        model.addAttribute("quizzes", quizzes);

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "quizList"; // admin view
        } else {
            return "quiz"; // user view
        }
    }

    @GetMapping("/addQuiz")
    public String addQuizPage(Model model) {
        model.addAttribute("question", new Question());
        return "addQuiz";
    }

    @PostMapping("/addQuiz")
    public String addQuiz(@ModelAttribute Question question) {
        questionsService.addQuiz(question);
        return "redirect:/quiz/home";
    }

    @GetMapping("/editQuiz/{id}")
    public String editQuizPage(@PathVariable int id, Model model) {
        Question question = questionsService.getQuizById(id);
        model.addAttribute("question", question);
        return "editQuiz";
    }

    @PostMapping("/editQuiz")
    public String editQuiz(@ModelAttribute Question question) {
        questionsService.editQuiz(question);
        return "redirect:/quiz/home";
    }

    @PostMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable int id) {
        questionsService.deleteQuiz(id);
        return "redirect:/quiz/home";
    }

    @PostMapping("/submitAnswers")
    public String submitAnswers(@RequestParam List<String> answers, Model model, Principal principal) {
        List<Question> questions = questionsService.loadQuizzes();
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < answers.size() && questions.get(i).getCorrectAnswer().equals(answers.get(i))) {
                score++;
            }
        }
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        model.addAttribute("username", principal.getName());
        return "result";
    }
}
