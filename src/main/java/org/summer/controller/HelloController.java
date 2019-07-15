package org.summer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.summer.model.Result;

@RestController
public class HelloController {

    @RequestMapping(value = "/api/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
    }

    @RequestMapping(value = "/api/calc", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Result> calc(@RequestParam int left, @RequestParam int right) {
        Result rs = new Result(left, right, left+right);
        return new ResponseEntity<Result>(rs, HttpStatus.OK);
    }
}
