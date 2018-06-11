package ru.ibs.testing.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ibs.testing.site.dto.Test;
import ru.ibs.testing.site.dto.User;
import ru.ibs.testing.site.repos.TestRepo;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class MappingController {

    @Autowired
    private TestRepo testRepo;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String home() {
        return "home";
    }


    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            Test test,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        test.setAuthor(user);

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

        return "main";
    }

    @GetMapping("/user-tests/{user}")
    public String userTests(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Test test
    ) {
        Set<Test> messages = user.getTests();

        model.addAttribute("messages", messages);
        model.addAttribute("test", test);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/user-tests/{user}")
    public String updateTests(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Test test,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (test.getAuthor().equals(currentUser)) {
//            if (!StringUtils.isEmpty(text)) {
//                test.setText(text);
//            }
//
//            if (!StringUtils.isEmpty(tag)) {
//                test.setTag(tag);
//            }


            testRepo.save(test);
        }

        return "redirect:/user-messages/" + user;
    }
}
