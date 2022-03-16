package org.launchcode.catfe.catfe.controllers;


import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.launchcode.catfe.catfe.models.*;
import org.launchcode.catfe.catfe.models.Cafe;
import org.launchcode.catfe.catfe.models.Category;
import org.launchcode.catfe.catfe.models.DietaryRestrictions;
import org.launchcode.catfe.catfe.models.DTO.FoodDTO;
import org.launchcode.catfe.catfe.models.Food;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Optional;


@Controller
public class FoodController {

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    FoodRepository foodRepository;



    @GetMapping("/add-food")
    public String addFoodItem(Model model){
        model.addAttribute("title", "Add Food");
        model.addAttribute(new  FoodDTO());
        model.addAttribute("foodCategories", Category.values());
        model.addAttribute("foodDiet", DietaryRestrictions.values());
        return "add-food";
    }
    @PostMapping("/add-food")
    public String submitFood(@ModelAttribute @Valid FoodDTO foodDTO, Errors errors, Model model, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile )throws IOException{
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Food");
            return "add-food";

        }
        Integer userId = (Integer) request.getSession().getAttribute("cafe");
        Cafe newCafe = cafeRepository.findById(userId).orElse(null);
        if (newCafe != null){
            model.addAttribute("food", foodRepository.findAll());
            Food newFood = new Food(foodDTO.getFoodName(), foodDTO.getFoodDescription(), foodDTO.getFoodCategory(),foodDTO.getPrice(),foodDTO.getFoodDiet());
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newCafe.setFood(newFood);
            newFood.setCafe(newCafe);
            Food savedFood = foodRepository.save(newFood);
            newFood.setPhoto("uploads/images/food-photos/" + savedFood.getId() + "/" + fileName);
            foodRepository.save(savedFood);
            String uploadDir = "uploads/images/food-photos/" + savedFood.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        }

        return "redirect:/cafe-profile-page";
    }

    @GetMapping ("/menu")
    public String displayMenu ( Model model, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("cafe");
        Optional<Cafe> result = cafeRepository.findById(userId);
        Cafe currentCafe  = result.get();
        model.addAttribute("title", currentCafe);
        model.addAttribute("food", currentCafe.getFood());
        return "menu";
    }

    @GetMapping("delete-food/{foodId}")
    public String displayDeleteFoodData(@PathVariable ("foodId") Integer foodId, HttpServletRequest request){
        Optional<Food> food = foodRepository.findById(foodId);
        Food removeFood = food.get();
        foodRepository.delete(removeFood);
        return "redirect:/cafe-profile-page";
    };


}
