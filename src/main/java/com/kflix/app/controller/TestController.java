package com.kflix.app.controller;

import com.kflix.app.dto.ContentCreateDto;
import com.kflix.app.dto.MyForm;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {


    @PostMapping("/form")
    public String handleFormUpload(@Valid ContentCreateDto form, BindingResult errors) {
        if (!form.getFile().isEmpty()) {
            try {
                byte[] bytes = form.getFile().getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // store the bytes somewhere
            return "redirect:uploadSuccess";
        }
        return "redirect:uploadFailure";
    }
}
