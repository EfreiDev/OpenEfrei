package dev.efrei.openefrei.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.utils.Response;

@RestController
public class MainController {

  @RequestMapping("/")
  public String handleMainGetRequest() {
    return Response.get(200, "API is working");
  }
 
}