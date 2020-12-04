package com.example.apicategory;

public class xmlClass {
    String NumCode;
    String CharCode;
    String Nominal;
    String Name;

    public xmlClass(String numCode, String charCode, String nominal, String name) {
        NumCode = numCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
    }

    public String getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public String getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }
}
