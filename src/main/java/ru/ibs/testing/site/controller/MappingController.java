package ru.ibs.testing.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ibs.testing.site.dto.*;
import ru.ibs.testing.site.repos.TestRepo;
import ru.ibs.testing.site.repos.UserRepo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MappingController {

    @Autowired
    private TestRepo testRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String greeting(
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        model.put("currentUser", currentUser);
        return "login";
    }


    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        Iterable<Test> tests = testRepo.findAll();

        model.put("tests", tests);
        model.put("currentUser", currentUser);

        return "main";
    }

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
    }
    @GetMapping("/test/{testID}")
    public String startTest(
            @PathVariable Long testID,
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        if (testID == 0) {
            return "redirect:/main";
        } else {
            Test test = getTestFromRepo(testID);
            model.put("test", test);
        }
        model.put("currentUser", currentUser);
        return "test";
    }


    @PostMapping("/test/{testID}")
    public String finishTest(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long testID,
            @RequestParam Map<String, String> form,
            @RequestParam("id") Test test
    ) {
        checkAnswers(currentUser, form, test);

        return "redirect:/result/" + testID;
    }


    @RequestMapping(value = "/makeTest/{testID}", method = RequestMethod.GET)
    public String userTests(
            @PathVariable Long testID,
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        Test test;
        if (testID == 0) {
            test = getNewTest(currentUser);
            testRepo.save(test);
            test = testRepo.findByName(test.getName()).get(0);
        } else {
            test = getTestFromRepo(testID);
        }
        model.put("test", test);
        model.put("currentUser", currentUser);
        return "testEdit";
    }

    @PostMapping("/makeTest/{testID}")
    public String updateTests(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long testID,
            @RequestParam Map<String, String> form,
            @RequestParam("id") Test test
    ) {
        fillTestFromForm(currentUser, form, test, 5, 5);

        testRepo.save(test);

        return "redirect:/main";
    }


    private void fillTestFromForm(User currentUser, Map<String, String> form, Test test, int qNumber, int aNumber) {

        test.setAuthor(currentUser);
        test.setName(form.get("t-" + test.getId()));

        Set<Question> questions = new HashSet<>();
        for (int i = 0; i < qNumber; i++) {
            Question q = new Question();
            long questionNumber = test.getId() + 1 + (i * 6);

            q.setName(form.get("q-" + questionNumber));
            q.setId(questionNumber);
            q.setTest(test);

            Set<Answer> answers = new HashSet<>();

            for (int ii = 0; ii < aNumber; ii++) {
                Answer a = new Answer();
                long answerNumber = test.getId() + 2 + (i * 6) + (ii * 1);
                a.setId(answerNumber);
                a.setName(
                        form.get("a-" + answerNumber));

                a.setPoints(Integer.parseInt(
                        form.get("p-" + answerNumber)));
                a.setQuestion(q);
                answers.add(a);
            }
            q.setAnswers(answers);

            questions.add(q);
        }
        test.setQuestions(questions);
    }

    private void checkAnswers(User currentUser, Map<String, String> form, Test test) {
        int score = 0;

        Question[] questions = test.getQuestions().toArray(new Question[0]);
        for (int i = 0; i < questions.length; i++) {
            String answer = form.get("q-" + questions[i].getId());

            Answer[] answers = questions[i].getAnswers().toArray(new Answer[0]);

            for (Answer answer1 : answers) {
                System.out.println(answer);
                System.out.println(answer1.getName());
                if (answer.equals(answer1.getName())) {
                    System.out.println("GOTIT + " + answers[i].getPoints());
                    score += answers[i].getPoints();
                }
            }
        }
        System.out.println(score);

        Result result = new Result();
        result.setPoints(score);
        result.setTest(test);
        result.setUser(currentUser);

        currentUser = userRepo.findByUsername(currentUser.getUsername());

        Result[] ress = currentUser.getResults().toArray(new Result[0]);
        Boolean flag = false;
        for (Result r: ress
             ) {
            if (r.getTest().getId().equals(result.getTest().getId())){
                r.setPoints(result.getPoints());
                flag = true;
            }
        }
        HashSet rrr = new HashSet(Arrays.asList(ress));

        if (!flag){
            rrr.add(result);
        }
        currentUser.setResults(rrr);
        userRepo.save(currentUser);
    }

    private Test getTestFromRepo(Long testID) {
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
        return test;
    }

    private Test getNewTest(User currentUser) {
        Test test = new Test();
        test.setAuthor(currentUser);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        test.setName("Тест пользователя " + currentUser.getUsername() + " От: " + sdf.format(timestamp));

        Set<Question> questions = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            Question q = new Question();
            q.setName("ВОПРОС " + i);

            Set<Answer> answers = new HashSet<>();
            for (int ii = 0; ii < 5; ii++) {
                Answer a = new Answer();
                a.setName("ответ  " + ii + " на вопрос " + i);
                a.setPoints(ii);
                a.setQuestion(q);
                answers.add(a);
            }
            q.setAnswers(answers);
            q.setTest(test);
            questions.add(q);
        }
        test.setQuestions(questions);
        return test;
    }

}
