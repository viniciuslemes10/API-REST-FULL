package br.com.erudio;

import br.com.erudio.numerics.NumericsUtils;
import br.com.erudio.numerics.math.MathUtils;
import br.com.erudio.numerics.math.NumericsAccounts;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
    private final AtomicLong counter = new AtomicLong();

    private final NumericsUtils numericsUtils = new NumericsUtils();

    private final MathUtils mathUtils = new MathUtils();

    private final NumericsAccounts numericsAccounts = new NumericsAccounts();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double greeting(@PathVariable(value = "numberOne") String numberOne,
                             @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        mathUtils.validateNumericsInput(numberOne, numberTwo);
        return numericsAccounts.sum(numericsUtils.converToDouble(numberOne),
                numericsUtils.converToDouble(numberTwo));
    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double subtract(@PathVariable(value = "numberOne") String numberOne,
                           @PathVariable(value = "numberTwo") String numberTwo) {
        mathUtils.validateNumericsInput(numberOne, numberTwo);
        return numericsAccounts.subtract(numericsUtils.converToDouble(numberOne),
                numericsUtils.converToDouble(numberTwo));
    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double division(@PathVariable(value = "numberOne") String numberOne,
                           @PathVariable(value = "numberTwo") String numberTwo) {
        mathUtils.validateNumericsInput(numberOne, numberTwo);

        mathUtils.validateNumericsEqualsZero(numberOne, numberTwo);
        return numericsAccounts.division(numericsUtils.converToDouble(numberOne),
                numericsUtils.converToDouble(numberTwo));
    }

    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
                           @PathVariable(value = "numberTwo") String numberTwo) {
        mathUtils.validateNumericsInput(numberOne, numberTwo);
        return numericsAccounts.multiplication(numericsUtils.converToDouble(numberOne),
                numericsUtils.converToDouble(numberTwo));
    }

    @RequestMapping(value = "/med/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double mean(@PathVariable(value = "numberOne") String numberOne,
                                 @PathVariable(value = "numberTwo") String numberTwo) {
        mathUtils.validateNumericsInput(numberOne, numberTwo);
        return numericsAccounts.mean(numericsUtils.converToDouble(numberOne),
                numericsUtils.converToDouble(numberTwo));
    }

    @RequestMapping(value = "/squareRoot/{numberOne}", method = RequestMethod.GET)
    public Double squareRoot(@PathVariable(value = "numberOne") String numberOne) {
        mathUtils.validateNumericsInput(numberOne);
        return numericsAccounts.squareRoot(numericsUtils.converToDouble(numberOne));
    }
   }