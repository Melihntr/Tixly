/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.tixly.ticket;

 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 
 @RestController
 @RequestMapping("/api")
 public class GreetingController {
 
     @GetMapping("/greeting")
     public String getGreeting() {
         return "Hello world";
     }
 }
 
