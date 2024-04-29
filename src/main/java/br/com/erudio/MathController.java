package br.com.erudio;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double greeting(@PathVariable(value = "numberOne") String numberOne,
                             @PathVariable(value = "numberTwo") String numberTwo) {
        return 1D;
    }
}
