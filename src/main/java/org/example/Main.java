package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.MiniLangLexer;
import org.example.MiniLangParser;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Ponemos la ruta de tu archivo de prueba (ej: programa.txt)
        String archivoPrueba = "programa.txt";

        try {
            // parseo sintactico
            MiniLangLexer lexer = new MiniLangLexer(CharStreams.fromFileName(archivoPrueba));
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
            System.err.println("\n[ERROR]: No se pudo leer el archivo " + archivoPrueba);
        }
    }
}