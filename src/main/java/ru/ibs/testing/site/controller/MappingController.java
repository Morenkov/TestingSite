package ru.ibs.testing.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;
import ru.ibs.testing.site.dto.*;
import ru.ibs.testing.site.repos.TestRepo;
import ru.ibs.testing.site.repos.UserRepo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class MappingController {

    @Autowired
    private TestRepo testRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String greeting(
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        model.put("currentUser", currentUser);
        return "login";
    }

    @GetMapping("/setSizes")
    public String setSizes(
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        model.put("currentUser", currentUser);
        return "set";
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
        User updateduser = userRepo.findByUsername(currentUser.getUsername());
        Set<Result> results = updateduser.getResults();

        model.put("results", results);
        model.put("user", updateduser);

        return "profileResult";
    }

    @PostMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        Boolean flag = false;
        currentUser = userRepo.findByUsername(currentUser.getUsername());
        if (!form.get("firstName").equals("")) {
            user.setFirstName(HtmlUtils.htmlEscape(form.get("firstName")));
            flag = true;
        }
        if (!form.get("lastName").equals("")) {
            user.setLastName(HtmlUtils.htmlEscape(form.get("lastName")));
            flag = true;
        }
        if (!form.get("thirdName").equals("")) {
            user.setThirdName(HtmlUtils.htmlEscape(form.get("thirdName")));
            flag = true;
        }
        if (!form.get("password").equals("")) {
            user.setPassword(passwordEncoder.encode(form.get("password")));
            flag = true;
        }
        if (!form.get("country").equals(user.getCountry())) {
            user.setCountry(HtmlUtils.htmlEscape(form.get("country")));
            flag = true;
        }
        if (!form.get("city").equals(user.getCity())) {
            user.setCity(HtmlUtils.htmlEscape(form.get("city")));
            flag = true;
        }
        if (flag) {
            userRepo.save(user);
        }
        return "redirect:/profile";
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


    @GetMapping("/makeTest/{testID}")
    public String userTests(
            @RequestParam(required = false) Integer q,
            @RequestParam(required = false) Integer a,
            @RequestParam(required = false, name = "errors") Boolean err,
            @PathVariable Long testID,
            @AuthenticationPrincipal User currentUser,
            Map<String, Object> model
    ) {
        Test test;
        if (testID == 0) {
            test = getNewTest(currentUser, q, a);
            testRepo.save(test);
            test = testRepo.findByName(test.getName()).get(0);
        } else {
            test = getTestFromRepo(testID);
        }
        if (err == null){
            err = false;
        }
        if (err){
        model.put("errors", err);
        }
        model.put("test", test);
        model.put("currentUser", currentUser);
        return "testEdit";
    }

    @ExceptionHandler({TransactionSystemException.class})
    @PostMapping("/makeTest/{testID}")
    public String updateTests(
            @RequestParam(required = false, name = "errors") Boolean err,
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long testID,
            @RequestParam Map<String, String> form
    ) {
        for (String key : form.keySet()
                ) {
            form.put(key, HtmlUtils.htmlEscape(form.get(key)));
        }
        Test test = testRepo.findById(testID).get();
        Integer qNumber = test.getQuestions().size();

        Question[] ans = test.getQuestions().toArray(new Question[0]);
        Integer aNumber = ans[0].getAnswers().size();

        fillTestFromForm(currentUser, form, test, qNumber, aNumber);
        Boolean flag = false;
        if (test.getName().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH.mm.ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            test.setName("Тест пользователя " + currentUser.getUsername() + " От: " + sdf.format(timestamp));
            flag = true;
        }
        for (Question q : test.getQuestions()
                ) {
            for (Answer a : q.getAnswers()
                    ) {
                if (a.getName().equals("")) {
                    a.setName("[пусто]");
                    flag = true;
                }
                if (flag) {
                    break;
                }
            }
            if (q.getName().equals("")) {
                q.setName("[пусто]");
                flag = true;
            }
            if (flag) {
                break;
            }
        }

        if (!flag) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Test>> constraintViolations = validator.validate(test);
            String errors = "";
            if (constraintViolations.size() > 0) {
                System.out.println("Constraint Violations occurred..");
                errors += "Constraint Violations occurred..\n";
                for (ConstraintViolation<Test> contraints : constraintViolations) {
                    System.out.println(contraints.getRootBeanClass().getSimpleName() +
                            "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                    errors += contraints.getRootBeanClass().getSimpleName() +
                            "." + contraints.getPropertyPath() + " " + contraints.getMessage() + "\n";
                }
                return "redirect:/makeTest/" + testID + "?errors";
            } else {
                testRepo.save(test);
            }
        }
        return "redirect:/main";
    }

    public static RedirectView safeRedirect(String url) {
        RedirectView rv = new RedirectView(url);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    private void fillTestFromForm(User currentUser, Map<String, String> form, Test test, int qNumber, int aNumber) {

        for (String key : form.keySet()
                ) {
            form.put(key, HtmlUtils.htmlEscape(form.get(key)));
        }

        test.setAuthor(currentUser);
        test.setName(form.get("t-" + test.getId()));

        Set<Question> questions = new HashSet<>();
        for (int i = 0; i < qNumber; i++) {
            Question q = new Question();
            long questionNumber = test.getId() + 1 + (i * (1 + aNumber));

            q.setName(form.get("q-" + questionNumber));
            q.setId(questionNumber);
            q.setTest(test);

            Set<Answer> answers = new HashSet<>();

            for (int ii = 0; ii < aNumber; ii++) {
                Answer a = new Answer();
                long answerNumber = test.getId() + 2 + (i * (1 + aNumber) + ii);
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
        for (String key : form.keySet()
                ) {
            form.put(key, HtmlUtils.htmlEscape(form.get(key)));
        }
        Question[] questions = test.getQuestions().toArray(new Question[0]);
        for (int i = 0; i < questions.length; i++) {
            String answer = form.get("q-" + questions[i].getId());

            Answer[] answers = questions[i].getAnswers().toArray(new Answer[0]);

            for (int ii = 0; ii < answers.length; ii++) {
                System.out.println(answers[ii].getName());
                if (answers[ii].getName().equals(answer)) {
                    score += answers[ii].getPoints();
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
        for (Result r : ress
                ) {
            if (r.getTest().getId().equals(result.getTest().getId())) {
                r.setPoints(result.getPoints());
                flag = true;
            }
        }
        HashSet rrr = new HashSet(Arrays.asList(ress));

        if (!flag) {
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

    private Test getNewTest(User currentUser, int que, int ans) {
        Test test = new Test();
        test.setAuthor(currentUser);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        test.setName("Тест пользователя " + currentUser.getUsername() + " От: " + sdf.format(timestamp));

        Set<Question> questions = new HashSet<>();

        for (int i = 0; i < que; i++) {
            Question q = new Question();
            q.setName("ВОПРОС " + i);

            Set<Answer> answers = new HashSet<>();
            for (int ii = 0; ii < ans; ii++) {
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
