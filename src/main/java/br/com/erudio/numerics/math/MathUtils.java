package br.com.erudio.numerics.math;

import br.com.erudio.exceptions.ExceptionDivisionZero;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.numerics.NumericsUtils;

public class MathUtils {

    private final NumericsUtils numericsUtils = new NumericsUtils();

    public void validateNumericsInput(String numberOne, String numberTwo) {
        if(!numericsUtils.isNumeric(numberOne) || !numericsUtils.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
    }

    public void validateNumericsInput(String numberOne) {
        if(!numericsUtils.isNumeric(numberOne)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
    }

    public void validateNumericsEqualsZero(String numberOne, String numberTwo) {
        if(!numericsUtils.isNumericZero(numberOne) || !numericsUtils.isNumericZero(numberTwo)) {
            throw new ExceptionDivisionZero("Please set a number greater than 0!");
        }
    }
}
