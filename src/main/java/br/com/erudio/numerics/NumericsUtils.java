package br.com.erudio.numerics;

public class NumericsUtils {

    public boolean isNumericZero(String strNumber) {
        Double numericNumber = converToDouble(strNumber);
        if(numericNumber == 0) {
            return false;
        }
        return true;
    }

    public Double converToDouble(String strNumber) {
        if(strNumber == null) return 0D;
        String number = strNumber.replaceAll(",", ".");
        if(isNumeric(strNumber)) return Double.parseDouble(number);

        return 0D;
    }

    public boolean isNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}
