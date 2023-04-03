package com.messenger.authandprofile.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("api/user")
public class AuthController {
    @GetMapping("{email:String}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String getUser(@PathVariable String email) {
        return email;
    }

//    @PostMapping("register")
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void RegisterUser(@RequestBody UserDto userDto) {
//        return;
//    }

    @PutMapping("edit")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void editUser() {

    }
}