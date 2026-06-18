package org.example;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private final Map<String, Simbolo> tabla = new HashMap<>();

    public void definir(String nombre, Tipo tipo) {
        tabla.put(nombre, new Simbolo(nombre, tipo));
    }

    public boolean existe(String nombre) {
        return tabla.containsKey(nombre);
    }

    public Tipo obtenerTipo(String nombre) {
        Simbolo s = tabla.get(nombre);
        return s != null ? s.tipo : null;
    }

    public void asignarValor(String nombre, Object valor) {
        Simbolo s = tabla.get(nombre);
        if (s != null) {
            s.valor = valor;
        }
    }

    public Object obtenerValor(String nombre) {
        Simbolo s = tabla.get(nombre);
        return s != null ? s.valor : null;
    }

    public static class Simbolo {
        public final String nombre;
        public final Tipo tipo;
        public Object valor;

        public Simbolo(String nombre, Tipo tipo) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.valor = null;
        }
    }
}
