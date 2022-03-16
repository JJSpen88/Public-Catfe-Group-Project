package org.launchcode.catfe.catfe.controllers;
import org.launchcode.catfe.catfe.models.Cafe;
import org.launchcode.catfe.catfe.models.Cat;
import org.launchcode.catfe.catfe.models.DTO.CatCreateDTO;
import org.launchcode.catfe.catfe.models.FileUploadUtil;
import org.launchcode.catfe.catfe.models.data.CafeRepository;
import org.launchcode.catfe.catfe.models.data.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


@Controller
public class CatController {

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    CatRepository catRepository;

    @GetMapping("/create-cat")
    public String displayCatCreateForm(Model model){
        model.addAttribute(new CatCreateDTO());
        model.addAttribute("title", "Create Cat");
        return "create-cat";
    }

    @PostMapping("/create-cat")
    public String processCatCreateForm(@ModelAttribute CatCreateDTO catCreateDTO,
                                       Errors errors, Model model, HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile )throws IOException {
        if (errors.hasErrors()){
            model.addAttribute("errors", errors);
        }
            model.addAttribute("title", "Create Cat");


        Integer userId = (Integer) request.getSession().getAttribute("cafe");
        Cafe newCafe = cafeRepository.findById(userId).orElse(null);

        if(newCafe != null){
            Cat newCat = new Cat (catCreateDTO.getCatName(), catCreateDTO.getCatAge(),
                    catCreateDTO.getCatBreed(), catCreateDTO.getCatGender(),
                    catCreateDTO.getCatWeightLbs(), catCreateDTO.getCatHeightInches(),
                    catCreateDTO.getCatDescription());
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            newCafe.setCat(newCat);
            newCat.setCafe(newCafe);
            newCat.setCatPhotos("uploads/images/cat-photos/" + newCat.getId() + "/" + fileName);

            catRepository.save(newCat);
            cafeRepository.save(newCafe);
            String uploadDir = "uploads/images/cat-photos/" + newCat.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        }


        return "redirect:/cafe-profile-page";
    }

    @GetMapping("/delete-cat/{catId}")
    public String processDeleteCatData(@PathVariable("catId") int catId, HttpServletRequest request){
        Optional<Cat> cat = catRepository.findById(catId);
        Cat removedCat = cat.get();
        catRepository.delete(removedCat);
        return "redirect:/cafe-profile-page";
    }

}
