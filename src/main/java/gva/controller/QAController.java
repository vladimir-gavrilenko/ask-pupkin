package gva.controller;

import gva.model.Answer;
import gva.model.Question;
import gva.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class QAController {
    private QAService qaService;

    @Autowired
    public QAController(QAService qaService) {
        this.qaService = qaService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String topQuestions(Model model) {
        List<Question> questions = qaService.findTopQuestions(Integer.MAX_VALUE);
        model.addAttribute("questions", questions);
        return "top";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String newQuestions(Model model) {
        List<Question> questions = qaService.findNewQuestions();
        model.addAttribute("questions", questions);
        return "new";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/question/{id}")
    public String question(Model model, @PathVariable int id) {
        Question question = qaService.findQuestionById(id);
        List<Answer> answers = qaService.findAnswersFor(question);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "question";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/answer")
    public String answer(@ModelAttribute Answer answer) {
        System.out.println(answer);
        return "redirect:/question/" + answer.getQuestion().getId();
    }
}
