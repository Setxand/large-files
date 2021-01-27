package com.example.demo;

import com.message.ResponseMessage;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
@Controller
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "mypage";
    }

    @PostMapping("/submitform")
    public String submitform(@RequestParam("myfile") MultipartFile myfile, Model model) {
        model.addAttribute("myfile", myfile.getOriginalFilename() + " " + myfile.getSize());
        return "mypage";
    }

    @PostMapping("/submitform-large")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("myfile") MultipartFile file) {
        String message = "";
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            InputStream inputStream = file.getInputStream();
            File targetFile = new File("src/main/resources/" + fileName);
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }


    }

}
