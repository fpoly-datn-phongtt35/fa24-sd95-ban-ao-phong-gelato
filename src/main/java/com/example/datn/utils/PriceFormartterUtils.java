package com.example.datn.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class PriceFormartterUtils {

    public static String numberFormart(Double price){
        NumberFormat numberFormat = new DecimalFormat("##.###");
        return numberFormat.format(numberFormat);
    }
}
