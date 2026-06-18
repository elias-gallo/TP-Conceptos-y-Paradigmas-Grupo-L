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

    @Override
    public Tipo visitPrintlnStmt(MiniLangParser.PrintlnStmtContext ctx) {
        visit(ctx.expr());
        return null;
    }

    @Override
    public Tipo visitIfStmt(MiniLangParser.IfStmtContext ctx) {
        Tipo tipoCond = visit(ctx.expr());
        if (tipoCond != Tipo.BOOL) {
            throw new RuntimeException("Error semántico: la condición del 'if' debe ser booleana");
        }
        for (var instr : ctx.instruccion()) {
            visit(instr);
        }
        return null;
    }

    @Override
    public Tipo visitRepeatStmt(MiniLangParser.RepeatStmtContext ctx) {
        for (var instr : ctx.instruccion()) {
            visit(instr);
        }
        Tipo tipoCond = visit(ctx.expr());
        if (tipoCond != Tipo.BOOL) {
            throw new RuntimeException("Error semántico: la condición del 'until' debe ser booleana");
        }
        return null;
    }

    @Override
    public Tipo visitTipo(MiniLangParser.TipoContext ctx) {
        if (ctx.T_ENTERO() != null) return Tipo.INT;
        if (ctx.T_FLOTANTE() != null) return Tipo.FLOAT;
        if (ctx.T_BOOL() != null) return Tipo.BOOL;
        if (ctx.T_TEXTO() != null) return Tipo.STRING;
        return null;
    }
}
