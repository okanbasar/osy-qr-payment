package com.tokeninc.altay.utilities;

/**
 * Created by Okan Engin Başar on 10.05.2019.
 */
public class CurrencyUtility {

    public static String getCurrencyCode(int iso4217) {
        switch(iso4217) {
            case 978:
                return "EUR";
            case 840:
                return "USD";
            case 949:
                return "TRY";
            default:
                return "###";
        }
    }
}
