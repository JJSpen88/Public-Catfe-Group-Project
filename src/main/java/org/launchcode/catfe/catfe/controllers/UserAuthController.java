package org.launchcode.catfe.catfe.controllers;


import org.launchcode.catfe.catfe.models.*;

import org.launchcode.catfe.catfe.models.DTO.UserReviewDTO;
import org.launchcode.catfe.catfe.models.User;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.CatRepository;
import org.launchcode.catfe.catfe.models.data.UserRepository;
import org.launchcode.catfe.catfe.models.DTO.UserLoginFormDTO;
import org.launchcode.catfe.catfe.models.DTO.UserRegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class UserAuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatRepository catRepository;

    @Autowired
    CafeRepository cafeRepository;


    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("")
    public String displayUserMain() {
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new UserRegistrationFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid UserRegistrationFormDTO userRegistrationFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser = userRepository.findByUsername(userRegistrationFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "register";
        }
        String password = userRegistrationFormDTO.getPassword();
        String verifyPassword = userRegistrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User newUser = new User(userRegistrationFormDTO.getFirstName(), userRegistrationFormDTO.getLastName(), userRegistrationFormDTO.getEmail(), userRegistrationFormDTO.getUsername(), userRegistrationFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/user-profile-page";
    }

    @GetMapping("user-profile-page")
    public String displayProfilePage(Model model, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        if (currentUser == null) {
            return "redirect:/login";
        } else {
            String title = "Welcome, " + currentUser.getUsername() + "!";
            model.addAttribute("favCats", currentUser.getFavCats());
            model.addAttribute("favCafes", currentUser.getFavCafes());
            model.addAttribute("title", title);
            model.addAttribute("thisUser", currentUser);
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("cafes", cafeRepository.findAll());
            model.addAttribute("reviews" , currentUser.getUserReviews());

        }
        return "user-profile-page";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new UserLoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid UserLoginFormDTO userLoginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }
        User theUser = userRepository.findByUsername(userLoginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = userLoginFormDTO.getPassword();
        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }
        setUserInSession(request.getSession(), theUser);

        return "redirect:/user-profile-page";
    }

    @GetMapping("/add-fav-cat/{catId}")
    public String addFavCat(@PathVariable("catId") int catId, HttpServletRequest request){
        Optional<Cat> cat = catRepository.findById(catId);
        Cat favCat = cat.get();
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        currentUser.addFavCat(favCat);
        userRepository.save(currentUser);
        return "redirect:/user-profile-page";
    }

    @GetMapping("/rem-fav-cat/{catId}")
    public String removeFavCat(@PathVariable("catId") int catId, HttpServletRequest request){
        Optional<Cat> cat = catRepository.findById(catId);
        Cat favCat = cat.get();
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        currentUser.removeFavCat(favCat);
        userRepository.save(currentUser);
        return "redirect:/user-profile-page";
    }

    @GetMapping("/add-fav-cafe/{cafeId}")
    public String addFavCafe(@PathVariable("cafeId") int cafeId, HttpServletRequest request){
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        Cafe favCafe = cafe.get();
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        currentUser.addFavCafe(favCafe);
        userRepository.save(currentUser);
        return "redirect:/user-profile-page";
    }

    @GetMapping("/rem-fav-cafe/{cafeId}")
    public String removeFavCafe(@PathVariable("cafeId") int cafeId, HttpServletRequest request){
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        Cafe favCafe = cafe.get();
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        currentUser.removeFavCafe(favCafe);
        userRepository.save(currentUser);
        return "redirect:/user-profile-page";
    }

    @GetMapping("/cafe/{cafeId}")
    public String displayPublicCafe(@PathVariable("cafeId") int cafeId, Model model, HttpServletRequest request){
        Optional<Cafe> cafe = cafeRepository.findById(cafeId);
        Integer userId = (Integer) request.getSession().getAttribute("user");
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();

        Cafe currentCafe = cafe.get();
        UserReviewDTO newUserReview = new UserReviewDTO();
        newUserReview.setCafe(currentCafe);

        if (currentCafe == null) {
            return "redirect:/user-profile-page";
        } else {
            model.addAttribute("cafe", currentCafe);
            model.addAttribute("thisUser", currentUser);
            model.addAttribute("Title", currentCafe.getCafeName());


            model.addAttribute("reviews", currentCafe.getUserReviews());

            model.addAttribute("myCats", currentCafe.getMyCats());


            model.addAttribute("review", currentCafe.getUserReviews());

            model.addAttribute("myCats", currentCafe.getMyCats());

            model.addAttribute("userReview", newUserReview);
            
            model.addAttribute("photos", currentCafe.getPhotos());
            model.addAttribute("food", currentCafe.getFood());

            if (currentUser == null) {
                return "redirect:/login";
            } else {
                model.addAttribute("cafe", currentCafe);
                model.addAttribute("Title", currentCafe.getCafeName());
                model.addAttribute("reviews", currentCafe.getUserReviews());
                model.addAttribute("myCats", currentCafe.getMyCats());
                model.addAttribute("review", currentCafe.getUserReviews());
                model.addAttribute("myCats", currentCafe.getMyCats());
                model.addAttribute("userReview", newUserReview);
                model.addAttribute("thisUser", currentUser);
            }
        }
        return "Cafe-public-page";
    }


    @GetMapping("/user-logout")
    public String processLogOut(HttpServletRequest request){
        request.getSession().setAttribute("user", null);
        return "redirect:/login";
    }

}





