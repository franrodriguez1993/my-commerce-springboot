package com.app.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Category;
import com.app.services.category.CategoryServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/category")
public class CategoryController extends BaseControllerImpl<Category, CategoryServiceImpl> {

}
