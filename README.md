# MiniLang - Intérprete con ANTLR4

Trabajo Práctico - Conceptos y Paradigmas de Lenguajes de Programación

## Integrantes

- Luca Lazarte
- Elías Gallo

## Variante asignada

**Variante 4: Repeat-Until**

Iteración con condición de corte invertida. El cuerpo del bucle se ejecuta al menos una vez y se repite hasta que la condición se cumple.

```java
repeat {
    // cuerpo del bucle
} until (condición);
```

## Descripción del lenguaje

MiniLang es un lenguaje imperativo simple con tipado estático. Soporta los siguientes tipos de datos:

- `entero` — números enteros
- `flotante` — números reales
- `bool` — valores booleanos (`true` / `false`)
- `texto` — cadenas de caracteres

### Estructura de un programa

```
var nombre : tipo = valor;
nombre = expresion;
print expresion;
if (condicion) {
    ...
} else {
    ...
}
repeat {
    ...
} until (condicion);
```

### Características

- Declaración de variables con tipo explícito: `var x : entero = 5;`
- Asignación: `x = x + 1;`
- Expresiones aritméticas: `+`, `-`, `*`, `/`
- Expresiones relacionales: `<`, `>`, `<=`, `>=`, `==`, `!=`
- Expresiones lógicas: `&` (and), `|` (or), `!` (not)
- Condicional: `if-else`
- Bucle: `repeat-until` (variante 4)
- Salida por consola: `print`
- Comentarios de línea: `//`

### Precedencia de operadores (de mayor a menor)

1. `(expresión)` — paréntesis
2. `!` — NOT lógico
3. `*` `/` — multiplicación y división
4. `+` `-` — suma y resta
5. `<` `>` `<=` `>=` — relacionales
6. `==` `!=` — igualdad
7. `&` — AND lógico
8. `|` — OR lógico

## Decisiones de diseño

### Tokens explícitos

Todas las palabras clave, operadores y signos se declararon como reglas léxicas con nombre propio (ej: `VAR : 'var'`, `IGUALDAD : '=='`) en lugar de escribirlos como literales inline. Esto permite:
- Constantes públicas en Java (`MiniLangParser.VAR`, `MiniLangParser.IGUALDAD`, etc.)
- Mensajes de error más claros
- Código más mantenible

### Visitor en lugar de Listener

Se utilizó el patrón Visitor (generado por ANTLR con `-visitor`) en lugar de Listener porque permite control explícito del recorrido del AST y separar la lógica de validación semántica de la ejecución en clases independientes.

### Labels en reglas sintácticas

Cada alternativa de la regla `expr` tiene un label (`# sumResExpr`, `# varRef`, etc.) para que ANTLR genere métodos `visit` específicos, evitando un único `visitExpr` con cadenas de `if-else`.

## Estructura del proyecto

```
src/
├── main/
│   ├── antlr4/
│   │   └── MiniLang.g4          ← Gramática del lenguaje
│   └── java/
│       └── org/example/
│           ├── Main.java         ← Punto de entrada con menú interactivo
│           ├── Tipo.java         ← Enum con los tipos de datos
│           ├── SymbolTable.java  ← Tabla de símbolos
│           ├── SemanticAnalyzer.java ← Analizador semántico (Visitor)
│           └── Interpreter.java  ← Intérprete (Visitor)
├── pom.xml                       ← Configuración Maven con ANTLR
└── test_*.txt                    ← Programas de ejemplo y prueba
```

## Instrucciones de compilación y ejecución

### Requisitos

- Java 21 o superior
- Apache Maven

### Compilación

```bash
mvn clean compile
```

### Ejecución

```bash
mvn exec:java -Dexec.mainClass=org.example.Main
```

Al ejecutarse se despliega un menú interactivo con los distintos programas de prueba:

```
========================================
      MENÚ DE PRUEBAS - COMPILADOR
========================================
1. Ejecutar: test_strings.txt
2. Ejecutar: test_aritmetica.txt
...
0. Salir
Seleccione una opción:
```

## Ejemplos de uso

### Hola Mundo

```
var saludo : texto = "Hola Mundo!";
print saludo;
```

**Salida:** `Hola Mundo!`

### Variables y aritmética

```
var x : entero = 20;
var y : entero = 10;
print x + y;
print x - y;
print x * y;
print x / y;
```

**Salida:** `30`, `10`, `200`, `2.0`

### Repeat-Until

```
var i : entero = 0;
repeat {
   i = i + 1;
   print i;
} until (i == 3);
```

**Salida:** `1`, `2`, `3`

### If-Else

```
var condicion : bool = false;
if (condicion) {
    print "Entró al if";
} else {
    print "Entró al else";
}
```

**Salida:** `Entró al else`
