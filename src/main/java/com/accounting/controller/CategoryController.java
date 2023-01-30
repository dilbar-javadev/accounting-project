package com.accounting.controller;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CategoryDto;
import com.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listCategories(Model model){
        model.addAttribute("categories", categoryService.listCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("newCategory") CategoryDto categoryDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            model.addAttribute("newCategory", categoryDto);

            return "/category/category-create";
        }

        if (categoryService.createCategory(categoryDto) != null){
            redirectAttributes.addFlashAttribute("error",categoryDto.getDescription()+" category already exists");
            return "redirect:/categories/list";
        }

        categoryService.createCategory(categoryDto);

        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model) throws AccountingAppException {
        model.addAttribute("category", categoryService.checkAndSetProductStatus(id));
        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws AccountingAppException {

        if(bindingResult.hasErrors()){

            model.addAttribute("category", category);

            return "/category/category-update";
        }

        if (categoryService.updateCategory(category) != null){
            redirectAttributes.addFlashAttribute("error",category.getDescription()+" category already exists");
            return "redirect:/categories/list";
        }

        categoryService.updateCategory(category);

        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) throws AccountingAppException {

        categoryService.deleteCategory(id);
        return "redirect:/categories/list";
    }
}
