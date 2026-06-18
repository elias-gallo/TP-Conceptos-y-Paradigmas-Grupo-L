package org.example;

public class SemanticAnalyzer extends MiniLangBaseVisitor<Tipo> {

    private final SymbolTable tabla;

    public SemanticAnalyzer(SymbolTable tabla) {
        this.tabla = tabla;
    }

    @Override
    public Tipo visitVarDecl(MiniLangParser.VarDeclContext ctx) {
        String nombre = ctx.ID().getText();
        if (tabla.existe(nombre)) {
            throw new RuntimeException("Error semántico: variable '" + nombre + "' ya declarada");
        }
        Tipo tipoDeclarado = visit(ctx.tipo());
        Tipo tipoExpr = visit(ctx.expr());
        if (tipoDeclarado != tipoExpr) {
            throw new RuntimeException("Error semántico: tipo incompatible en '" + nombre + "'");
        }
        tabla.definir(nombre, tipoDeclarado);
        return tipoDeclarado;
    }

    @Override
    public Tipo visitVarRef(MiniLangParser.VarRefContext ctx) {
        String nombre = ctx.ID().getText();
        if (!tabla.existe(nombre)) {
            throw new RuntimeException("Error semántico: variable '" + nombre + "' no declarada");
        }
        return tabla.obtenerTipo(nombre);
    }

    @Override
    public Tipo visitAsignacion(MiniLangParser.AsignacionContext ctx) {
        String nombre = ctx.ID().getText();
        if (!tabla.existe(nombre)) {
            throw new RuntimeException("Error semántico: variable '" + nombre + "' no declarada");
        }
        Tipo tipoVar = tabla.obtenerTipo(nombre);
        Tipo tipoExpr = visit(ctx.expr());
        if (tipoVar != tipoExpr) {
            throw new RuntimeException("Error semántico: tipo incompatible en '" + nombre + "'");
        }
        return tipoVar;
    }
}
