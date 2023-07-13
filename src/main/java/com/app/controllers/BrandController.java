package com.app.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Brand;
import com.app.services.brand.BrandServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/brand")
public class BrandController extends BaseControllerImpl<Brand, BrandServiceImpl> {

}
