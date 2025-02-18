package com.iticbcn.danimerida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Metode per lectura per linea de comandes
    public static String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error en la lectura de entrada", e);
        }
    }
}
