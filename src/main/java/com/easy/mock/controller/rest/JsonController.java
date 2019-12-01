package com.easy.mock.controller.rest;

import com.easy.mock.model.Model;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
public class JsonController {

    private Model model;

    @Autowired
    public JsonController(Model model) {
        this.model = model;
    }

    @PostMapping("receive-json")
    public ResponseEntity<Object> getJson(@RequestBody Model modelInput) {
        this.model = modelInput;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-json")
    public ResponseEntity<Object> produceJson() {
        JSONParser parser = new JSONParser();
        InputStream stream = new ByteArrayInputStream(model.getValue().getBytes(StandardCharsets.UTF_8));

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(stream, "UTF-8"));
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("clear")
    public ResponseEntity<Object>clear() {
        this.model.setValue("{}");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
