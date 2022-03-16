package org.launchcode.catfe.catfe.controllers;

import org.launchcode.catfe.catfe.models.Cafe;
import org.launchcode.catfe.catfe.models.Search;
import org.launchcode.catfe.catfe.models.User;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    UserRepository userRepository;



    @GetMapping("search")
    public String displayCafeResults(String searchTerm, Model model, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();

        Iterable<Cafe> cafes;
        if(searchTerm.toLowerCase().equals("all") || searchTerm.toLowerCase().equals("")){
            cafes = cafeRepository.findAll();
        } else {
            cafes = Search.findByName(searchTerm, cafeRepository.findAll());
        }
        model.addAttribute("title2", "Cafes matching " + searchTerm);
        model.addAttribute("cafes", cafes);


        model.addAttribute("thisUser", currentUser);
        model.addAttribute("username", currentUser.getUsername());

        return "user-profile-page";
    }
}
