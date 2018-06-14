package ru.ibs.testing.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ibs.testing.site.dto.Answer;
import ru.ibs.testing.site.dto.Question;
import ru.ibs.testing.site.dto.Test;
import ru.ibs.testing.site.dto.User;
import ru.ibs.testing.site.repos.TestRepo;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MappingController {

    @Autowired
    private TestRepo testRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "login";
    }


    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        Iterable<Test> tests;
        tests = testRepo.findAll();

        model.addAttribute("tests", tests);
        model.addAttribute("currentUser", currentUser);

        return "main";
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        currentUser = userRepo.findByUsername(currentUser.getUsername());
        Set<Result> results = currentUser.getResults();

        model.put("results", results);
        model.put("currentUser", currentUser);

        return "profileResult";
    }

    @GetMapping("/result/{testID}")
    public String userResult(
            @PathVariable Long testID,
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        Test test = getTestFromRepo(testID);
        currentUser = userRepo.findByUsername(currentUser.getUsername());
        Set<Result> res = currentUser.getResults();

        for (Result result : res) {
            if (result.getTest().getId().equals(test.getId())) {
                model.put("result", result);
            }
        }
        model.put("test", test);
        model.put("currentUser", currentUser);
        return "result";
=======
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User currentUser,
            Test test,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        test.setAuthor(currentUser);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", test);
        } else {

            model.addAttribute("message", null);

            testRepo.save(test);
        }

        Iterable<Test> tests = testRepo.findAll();

        model.addAttribute("tests", tests);
        model.addAttribute("currentUser", currentUser);

        return "main";
>>>>>>> parent of 15adf41... 4
    }
    @GetMapping("/test/{testID}")
    public String startTest(
            @PathVariable Long testID,
=======
    @PostMapping("/main")
    public String add(
>>>>>>> parent of 15adf41... 4
            @AuthenticationPrincipal User currentUser,
            Test test,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        test.setAuthor(currentUser);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", test);
        } else {

            model.addAttribute("message", null);

            testRepo.save(test);
        }

<<<<<<< HEAD
<<<<<<< HEAD

    @PostMapping("/test/{testID}")
    public String finishTest(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long testID,
            @RequestParam Map<String, String> form,
            @RequestParam("id") Test test
    ) {
        checkAnswers(currentUser, form, test);
=======
        Iterable<Test> tests = testRepo.findAll();
>>>>>>> parent of 15adf41... 4

        model.addAttribute("tests", tests);
        model.addAttribute("currentUser", currentUser);

<<<<<<< HEAD

    @RequestMapping(value = "/makeTest/{testID}", method = RequestMethod.GET)
=======
        return "main";
    }

    @GetMapping("/user-tests/{testID}")
>>>>>>> parent of 15adf41... 4
=======
    @GetMapping("/user-tests/{testID}")
>>>>>>> parent of 15adf41... 4
    public String userTests(
            @PathVariable Long testID,
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        if (testID == 0) {
            Test test = new Test();
            test.setAuthor(currentUser);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            test.setName("test of " + currentUser.getUsername() + " created at: " + sdf.format(timestamp));

            Set<Question> questions = new HashSet<>();

            for (int i = 0; i < 5; i++) {
                Question q = new Question();
                q.setName("ВОПРОС номер " + i);

                Set<Answer> answers = new HashSet<>();
                for (int ii = 0; ii < 5; ii++) {
                    Answer a = new Answer();
                    a.setName("ответ номер " + ii + " на вопрос номер " + i);
                    a.setPoints(ii);
                    a.setQuestion(q);
                    answers.add(a);
                }
                q.setAnswers(answers);
                q.setTest(test);
                questions.add(q);
            }
            test.setQuestions(questions);
            testRepo.save(test);
            List<Test> listTest = testRepo.findByName(test.getName());
            model.put("test", listTest.get(0));

        } else {
            Test test = null;
            Iterable<Test> listTest = testRepo.findAll();
            for (Test item :
                    listTest) {
                System.out.println(item.getId());
                System.out.println(testID);
                if (item.getId().equals(testID)) {
                    test = item;
                    break;
                }
            }
            if (test == null) {
                System.out.println("Not found");
            }
            model.put("test", test);
        }
        model.put("currentUser", currentUser);
        return "testEdit";

    }

    @PostMapping("/user-tests/{testID}")
    public String updateTests(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long testID,
            @RequestParam Map<String, String> form,
            @RequestParam("id") Test test
    ) throws IOException {
        test.setAuthor(currentUser);
        test.setName(form.get("t-" + test.getId()));

        Set<Question> questions = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Question q = new Question();
            long questionNumber = test.getId() + 1 + (i * 6);

            q.setName(form.get("q-" + questionNumber));
            q.setId(questionNumber);
            q.setTest(test);

            Set<Answer> answers = new HashSet<>();
            for (int ii = 0; ii < 5; ii++) {
                Answer a = new Answer();
                long answerNumber = test.getId() + 2 + (i * 6) + (ii * 1);
                a.setId(answerNumber);
                a.setName(
                        form.get("a-" + answerNumber));

                System.out.println(
                        form.get("p-" + (answerNumber)));

                a.setPoints(Integer.parseInt(
                        form.get("p-" + (answerNumber))));
                a.setQuestion(q);
                answers.add(a);
            }
            q.setAnswers(answers);

            questions.add(q);
        }
        test.setQuestions(questions);


<<<<<<< HEAD
<<<<<<< HEAD
        test.setName("Тест пользователя " + currentUser.getUsername() + " От: " + sdf.format(timestamp));
=======
        System.out.println(form.keySet());
>>>>>>> parent of 15adf41... 4
=======
        System.out.println(form.keySet());
>>>>>>> parent of 15adf41... 4

        testRepo.save(test);
        return "redirect:/user-tests/" + testID;
    }
}
