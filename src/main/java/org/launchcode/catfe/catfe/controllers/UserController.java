package org.launchcode.catfe.catfe.controllers;


import org.launchcode.catfe.catfe.models.DTO.UserLoginFormDTO;
import org.launchcode.catfe.catfe.models.User;
import org.launchcode.catfe.catfe.models.UserReview;
import org.launchcode.catfe.catfe.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

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


    @GetMapping("/delete-user")
    public String displayDeleteUserData(Model model) {
        model.addAttribute("title", "Delete User");
        return "delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUser(HttpServletRequest request, UserReview userReview) {
        User currentUser = getUserFromSession(request.getSession());
        Set<UserReview> userReviews;
        userRepository.delete(currentUser);
        return "redirect:/login";
    }

    @GetMapping("/edit-user")
    public String editUser(HttpServletRequest request, Model model){
        Integer userId = (Integer) request.getSession().getAttribute("user");
        User existingUser = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", existingUser);

        return "edit-user";
    }

    @PostMapping("/edit-user")
    public String updateUser(@ModelAttribute @Valid User existingUser, Errors errors, Model model, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("user");
        User updatedUser = userRepository.findById(userId).orElse(null);
        int strength = 10; // work factor of bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(existingUser.getPassword());

        updatedUser.setFirstName(existingUser.getFirstName());
        updatedUser.setLastName(existingUser.getLastName());
        updatedUser.setEmail(existingUser.getEmail());
        updatedUser.setUsername(existingUser.getUsername());
        updatedUser.setPwHash(encodedPassword);
        userRepository.save(updatedUser);

        return "redirect:/login";
    }

}

