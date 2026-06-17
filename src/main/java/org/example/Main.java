package org.example;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.IOException;

import org.example.MiniLangLexer;
import org.example.MiniLangParser;

public class Main {
    public static void main(String[] args) {
        String program = "programa.txt";

        System.out.println("Interpreting file: " + program);

        try {
            CharStream input = CharStreams.fromFileName(program);
            MiniLangLexer lexer = new MiniLangLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MiniLangParser parser = new MiniLangParser(tokens);

            ParseTree tree = parser.programa();

            System.out.println("Interpretation finished");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}