package com.easy.mock.controller.rest;

import com.easy.mock.model.Model;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
public class JsonController {

    @Autowired
    private Model model;

    @PostMapping("/receive-json")
    public ResponseEntity<Object> getJson(@RequestBody Model modelInput) {
        this.model = modelInput;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-json")
    public ResponseEntity<Object> produceJson() {
        JSONParser parser = new JSONParser();

        InputStream stream = new ByteArrayInputStream(model.getValue().getBytes(StandardCharsets.UTF_8));
        JSONObject j = null;
        try {
            j = (JSONObject) parser.parse(new InputStreamReader(stream, "UTF-8"));
        } catch (Exception e) {
        }

        return new ResponseEntity<>(j, HttpStatus.OK);
    }

}
