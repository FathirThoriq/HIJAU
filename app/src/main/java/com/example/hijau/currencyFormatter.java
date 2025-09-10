package com.example.hijau;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class currencyFormatter {
    // ubah tipe data long ke big decimal
    public static String formatRp(long nominal) {
        return formatRp(BigDecimal.valueOf(nominal));
    }

    // jika nominal berupa double, ubah ke big decimal
    public static String formatRp(double nominal) {
        return formatRp(BigDecimal.valueOf(nominal));
    }

    // format uang ke style rupiah
    public static  String formatRp(BigDecimal nominal) {
        if (nominal == null) nominal = BigDecimal.ZERO;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        df.setGroupingUsed(true);

        return  "Rp " + df.format(nominal);
    }

// sisa formatter buat convert string (Rp 30.000) ke big decimal ( 30000 )
}
