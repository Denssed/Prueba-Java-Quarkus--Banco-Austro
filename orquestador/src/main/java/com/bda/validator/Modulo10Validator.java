package com.bda.validator;

public final class Modulo10Validator {

    private static final int[] COEFICIENTES = {2, 1, 2, 1, 2, 1, 2, 1, 2};

    private Modulo10Validator() {
    }

    public static boolean esValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }
        int tercerDigito = cedula.charAt(2) - '0';
        if (tercerDigito >= 6) {
            return false;
        }
        int suma = 0;
        for (int i = 0; i < COEFICIENTES.length; i++) {
            int producto = (cedula.charAt(i) - '0') * COEFICIENTES[i];
            suma += producto >= 10 ? producto - 9 : producto;
        }
        int digitoVerificador = cedula.charAt(9) - '0';
        int resultado = (10 - (suma % 10)) % 10;
        return resultado == digitoVerificador;
    }
}
