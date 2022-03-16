package org.launchcode.catfe.catfe.controllers;

import org.apache.tomcat.util.json.ParseException;
import org.launchcode.catfe.catfe.models.Cafe;

import org.launchcode.catfe.catfe.models.DTO.CafeLoginDTO;
import org.launchcode.catfe.catfe.models.DTO.CafeRegisterFormDTO;
import org.launchcode.catfe.catfe.models.FileUploadUtil;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Optional;

@Controller
public class CafeAuthController {

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    UserReviewRepository userReviewRepository;


    private static final String cafeSessionKey = "cafe";

    public Cafe getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(cafeSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<Cafe> cafe = cafeRepository.findById(userId);

        if (cafe.isEmpty()) {
            return null;
        }

        return cafe.get();
    }

    private static void setUserInSession(HttpSession session, Cafe cafe) {
        session.setAttribute(cafeSessionKey, cafe.getId());
    }

    @GetMapping("/cafe-login")
    public String displayCafeLoginForm(Model model){
        model.addAttribute(new CafeLoginDTO());
        model.addAttribute("title", "Log In");
        return "cafe-login";
    }

    @PostMapping("/cafe-login")
    public String processLoginForm(@ModelAttribute @Valid CafeLoginDTO cafeLoginDTO,
                                   Errors errors, Model model,
                                   HttpServletRequest request) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "cafe-login";
        }
        Cafe theCafe = cafeRepository.findByUsername(cafeLoginDTO.getUsername());

        if (theCafe == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "cafe-login";
        }

        String password = cafeLoginDTO.getPassword();
        if (!theCafe.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "cafe-login";
        }

        setUserInSession(request.getSession(), theCafe);


        return "redirect:/cafe-profile-page";
    }


    @GetMapping("/cafe-registration")
    public String showRegisterForm(Model model){

        model.addAttribute(new CafeRegisterFormDTO());
        model.addAttribute("title", "Register Cafe");
        return "cafe-registration";
    }

    @PostMapping("/cafe-registration")
    public String submitRegisterForm(@ModelAttribute @Valid CafeRegisterFormDTO cafeRegisterFormDTO,
                                     Errors errors, Model model,
                                     HttpServletRequest request) throws URISyntaxException, IOException, ParseException {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Register Cafe");

            return "cafe-registration";
        }

        Cafe existingCafe = cafeRepository.findByUsername(cafeRegisterFormDTO.getUsername());


        if (existingCafe != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "cafe-registration";
        }

        String password = cafeRegisterFormDTO.getPassword();
        String verifyPassword = cafeRegisterFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "cafe-registration";
        }

        Cafe newCafe = new Cafe(cafeRegisterFormDTO.getUsername(),cafeRegisterFormDTO.getPassword(),
                cafeRegisterFormDTO.getCafeName(), cafeRegisterFormDTO.getStreetAddress(),
                cafeRegisterFormDTO.getStateLocation(), cafeRegisterFormDTO.getZipCode(),
                cafeRegisterFormDTO.getPhoneNum(), cafeRegisterFormDTO.getEmailAddress(),
                cafeRegisterFormDTO.getInstaLink(), cafeRegisterFormDTO.getFbLink(),
                cafeRegisterFormDTO.getTwitterLink(), cafeRegisterFormDTO.getCafeDescription(),
                cafeRegisterFormDTO.getCafeRules(), cafeRegisterFormDTO.getAdmissionPrice());
        newCafe.create_coords();

        cafeRepository.save(newCafe);

        Cafe theCafe = cafeRepository.findByUsername(cafeRegisterFormDTO.getUsername());
        setUserInSession(request.getSession(), theCafe);



        return "redirect:/cafe-profile-page";
    }

    @GetMapping("/cafe-delete")
    public String displayDeleteCafe(Model model){
        model.addAttribute("title", "Delete Account");
        return "cafe-delete";
    }

    @PostMapping("/cafe-delete")
    public String submitDeleteCafe(HttpServletRequest request){

        Cafe currentCafe = getUserFromSession(request.getSession());
        cafeRepository.delete(currentCafe);
        return "redirect:/cafe-login";
    }

    @GetMapping("/cafe-profile-page")
    public String displayProfilePage(Model model, HttpServletRequest request){
        Integer userId =  (Integer) request.getSession().getAttribute("cafe");
        Optional<Cafe> result = cafeRepository.findById(userId);

        Cafe currentCafe = result.get();
        if(currentCafe == null){
            return "redirect:/cafe-login";
        } else {

            model.addAttribute("thisCafe", currentCafe);
                    String title = "Welcome, " + currentCafe.getCafeName() + "!";
            model.addAttribute("myCats", currentCafe.getMyCats());
            model.addAttribute("cafeName", currentCafe.getCafeName());
            model.addAttribute("reviews" , currentCafe.getUserReviews());
            model.addAttribute("title", title);
            model.addAttribute("menu",currentCafe.getFood());



        }

        return "cafe-profile-page";
    }
    @GetMapping("/cafe-logout")
    public String processLogOut(HttpServletRequest request){
        request.getSession().setAttribute("cafe", null);
        return "redirect:/cafe-login";
    }

    @GetMapping("/edit-cafe")
    public String editCafeDetails(HttpServletRequest request, Model model){
        Integer cafeId = (Integer) request.getSession().getAttribute("cafe");
        Cafe existingCafe = cafeRepository.findById(cafeId).orElse(null);
        model.addAttribute("cafe", existingCafe);

        return "edit-cafe";
    }

    @PostMapping("/edit-cafe")
    public String updateCafeDetails(@ModelAttribute Cafe existingCafe, Model model, HttpServletRequest request) throws IOException, ParseException {
        Integer cafeId = (Integer) request.getSession().getAttribute("cafe");
        Cafe updatedCafe = cafeRepository.findById(cafeId).orElse(null);
        int strength = 10; // work factor of bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(existingCafe.getPassword());

        updatedCafe.setUsername(existingCafe.getUsername());
        updatedCafe.setCafeName(existingCafe.getCafeName());
        updatedCafe.setStreetAddress(existingCafe.getStreetAddress());
        updatedCafe.setStateLocation(existingCafe.getStateLocation());
        updatedCafe.setZipCode(existingCafe.getZipCode());
        updatedCafe.setPhoneNum(existingCafe.getPhoneNum());
        updatedCafe.setEmailAddress(existingCafe.getEmailAddress());
        updatedCafe.setInstaLink(existingCafe.getInstaLink());
        updatedCafe.setFbLink(existingCafe.getFbLink());
        updatedCafe.setTwitterLink(existingCafe.getTwitterLink());
        updatedCafe.setCafeDescription(existingCafe.getCafeDescription());
        updatedCafe.setCafeRules(existingCafe.getCafeRules());
        updatedCafe.setPwHash(encodedPassword);
        updatedCafe.create_coords();
        cafeRepository.save(updatedCafe);
        return "redirect:/cafe-login";
    }

    @GetMapping("/Cafe-public-page")
    public String displayCafePublic(){


        return "Cafe-public-page";
    }

    @PostMapping("/add-photos")
    public String Photos(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Integer userId = (Integer) request.getSession().getAttribute("cafe");
        Cafe newCafe = cafeRepository.findById(userId).orElse(null);
        newCafe.addPhotos("/uploads/images/cafe-photos/"+newCafe.getId()+"/"+fileName);
        String uploadDir = "uploads/images/cafe-photos/" + newCafe.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        cafeRepository.save(newCafe);
        return "redirect:/cafe-profile-page";
    }

}