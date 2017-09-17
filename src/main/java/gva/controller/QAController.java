package gva.controller;

import gva.model.Answer;
import gva.model.Question;
import gva.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QAController {
    private QAService qaService;

    @Autowired
    public QAController(QAService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("/")
    public String topQuestions(Model model) {
        List<Question> questions = qaService.findTopQuestions(Integer.MAX_VALUE);
        model.addAttribute("questions", questions);
        return "top";
    }

    @GetMapping("/new")
    public String newQuestions(Model model) {
        List<Question> questions = qaService.findNewQuestions();
        model.addAttribute("questions", questions);
        return "new";
    }

    @GetMapping("/question/{id}")
    public String question(Model model, @PathVariable Long id) {
        Question question = qaService.findQuestionById(id);
        List<Answer> answers = qaService.findAnswersFor(question);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "question";
    }

    @PostMapping("/answer")
    public String answer(@ModelAttribute Answer answer) {
        System.out.println(answer);
        return "redirect:/question/" + answer.getQuestion().getId();
    }
}
