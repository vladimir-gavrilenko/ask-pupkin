package gva.controller;

import gva.model.Question;
import gva.service.AnswerService;
import gva.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {
    private QuestionService questionService;
    private AnswerService answerService;

    @Autowired
    public MainController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model model) {
        List<Question> questions = questionService.getTop(Integer.MAX_VALUE);
        System.out.println(questions);
        model.addAttribute("questions", questions);
        return "index";
    }
}
