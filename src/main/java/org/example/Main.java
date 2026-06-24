package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.MiniLangLexer;
import org.example.MiniLangParser;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String[] OPCIONES = {
            "test_strings.txt",
            "test_aritmetica.txt",
            "test_if_else.txt",
            "test_repeat_until.txt",
            "test_booleanos.txt",
            "test_comparaciones.txt",
            "test_expresiones.txt",
            "test_errores_semanticos.txt",
            "test_variables_no_declaradas.txt",
            "test_division_cero.txt"
    };

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("      MENÚ DE PRUEBAS - COMPILADOR      ");
            System.out.println("========================================");

            for (int i = 0; i < OPCIONES.length; i++) {
                System.out.println((i + 1) + ". Ejecutar: " + OPCIONES[i]);
            }
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            }

            if (opcion == 0) {
                System.out.println("Saliendo del programa...");
                break;
            }

            // opcion dentro de los limites
            if (opcion > 0 && opcion <= OPCIONES.length) {
                String archivoElegido = OPCIONES[opcion - 1];
                System.out.println("\n--- Leyendo archivo '" + archivoElegido + "' ---");

                ejecutarCompilador(archivoElegido);
            } else {
                System.out.println("Opción incorrecta. Intente de nuevo.");
            }
            System.out.println("\n[Presione ENTER para continuar y volver al menú...]");
            scanner.nextLine();
        }
        scanner.close();
    }
        private static void ejecutarCompilador(String entrada){
        try {
            // parseo sintactico
            MiniLangLexer lexer = new MiniLangLexer(CharStreams.fromFileName(entrada));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniLangParser parser = new MiniLangParser(tokens);

            // arbol
            ParseTree tree = parser.programa();

            // analisis semántico
            SymbolTable tablaSimbolos = new SymbolTable();
            SemanticAnalyzer analizadoSemantico = new SemanticAnalyzer(tablaSimbolos);
            analizadoSemantico.visit(tree);


            // interprete
            Interpreter interprete = new Interpreter(tablaSimbolos);
            interprete.visit(tree);

        } catch (RuntimeException e) {
            System.err.println("\n[ERROR EN TIEMPO DE COMPILACIÓN/EJECUCIÓN]: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\n[ERROR]: No se pudo leer el archivo " + entrada);
        }
        }
}