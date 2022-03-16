package org.launchcode.catfe.catfe.controllers;


import org.launchcode.catfe.catfe.models.Cafe;
import org.launchcode.catfe.catfe.models.DTO.UserReviewDTO;
import org.launchcode.catfe.catfe.models.User;
import org.launchcode.catfe.catfe.models.UserReview;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.UserRepository;
import org.launchcode.catfe.catfe.models.data.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;


@Controller
public class UserReviewController {

    @Autowired
    UserReviewRepository userReviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CafeRepository cafeRepository;



    @GetMapping("/user-review")
    public String displayUserReviewForm(Model model, HttpServletRequest request) {
        UserReviewDTO newUserReview = new UserReviewDTO();
        Integer cafeId = (Integer) request.getSession().getAttribute("cafe");
        Cafe currentCafe = cafeRepository.findById(cafeId).orElse(null);
        newUserReview.setCafe(currentCafe);
        model.addAttribute("userReview", newUserReview);
        return "user-review";
    }

    @PostMapping("/user-review")
    public String processUserReviewForm(@ModelAttribute UserReviewDTO userReviewDTO, HttpServletRequest request, Model model) {

        Integer userId = (Integer) request.getSession().getAttribute("user");
        User existingUser = userRepository.findById(userId).orElse(null);
        Cafe existingCafe = userReviewDTO.getCafe();
        UserReview newReview = new UserReview();

        newReview.setReviewTitle(userReviewDTO.getReviewTitle());
        newReview.setUserReview(userReviewDTO.getUserReview());
        newReview.setUserRating(userReviewDTO.getUserRating());

        newReview.setCafe(existingCafe);
        newReview.setUser(existingUser);

        existingUser.setUserReview(newReview);
        existingCafe.addReview(newReview);
        userReviewRepository.save(newReview);

        return "redirect:/cafe/" + existingCafe.getId();
    }

    @GetMapping("/user-review-delete")
    public String displayDeleteUserReview(Model model){
        model.addAttribute("title", "Delete Account");
        return "user-review-delete";
    }

    @PostMapping("/user-review-delete")
    public String processDeleteUserReview(HttpServletRequest request){

        Integer reviewId = (Integer) request.getSession().getAttribute("userReview");
        UserReview existingReview = userReviewRepository.findById(2).orElse(null);
        userReviewRepository.delete(existingReview);
        return "redirect:/user-profile-page";
    }


}