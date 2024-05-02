package br.com.erudio.numerics.math;

import br.com.erudio.numerics.NumericsUtils;

import java.text.DecimalFormat;

public class NumericsAccounts {
    private final NumericsUtils numericsUtils = new NumericsUtils();

    public Double sum(Double numberOne,
                      Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtract(Double numberOne,
                           Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double division(Double numberOne,
                           Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne,
                                 Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double mean(Double numberOne,
                       Double numberTwo) {
        return (numberOne + numberTwo) / 2;
    }

    public Double squareRoot(Double numberOne) {
        double root = Math.sqrt(numberOne);

        DecimalFormat formatNumber = new DecimalFormat("#.##");

        return numericsUtils.converToDouble(formatNumber.format(root));
    }
}
