package org.example;

public class Interpreter extends MiniLangBaseVisitor<Object> {

    private final SymbolTable tabla;

    public Interpreter(SymbolTable tabla) {
        this.tabla = tabla;
    }

    @Override
    public Object visitVarDecl(MiniLangParser.VarDeclContext ctx) {
        String nombre = ctx.ID().getText();
        Object valor = visit(ctx.expr());
        tabla.asignarValor(nombre, valor);
        return null;
    }

    @Override
    public Object visitAsignacion(MiniLangParser.AsignacionContext ctx) {
        String nombre = ctx.ID().getText();
        Object valor = visit(ctx.expr());
        tabla.asignarValor(nombre, valor);
        return null;
    }

    @Override
    public Object visitPrintlnStmt(MiniLangParser.PrintlnStmtContext ctx) {
        Object valor = visit(ctx.expr());
        System.out.println(valor);
        return null;
    }

    @Override
    public Object visitIfStmt(MiniLangParser.IfStmtContext ctx) {
        boolean condicion = (boolean) visit(ctx.expr());
        if (condicion) {
            for (var instr : ctx.ifBody) {
                visit(instr);
            }
        } else {
            for (var instr : ctx.elseBody) {
                visit(instr);
            }
        }
        return null;
    }

    @Override
    public Object visitRepeatStmt(MiniLangParser.RepeatStmtContext ctx) {
        do {
            for (var instr : ctx.instruccion()) {
                visit(instr);
            }
        } while (!((boolean) visit(ctx.expr())));
        return null;
    }

    @Override
    public Object visitMulDivExpr(MiniLangParser.MulDivExprContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        if (op.equals("*") && left instanceof Integer && right instanceof Integer) {
            return (Integer) left * (Integer) right;
        }
        double a = ((Number) left).doubleValue();
        double b = ((Number) right).doubleValue();
        return switch (op) {
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new RuntimeException("Operador desconocido: " + op);
        };
    }

    @Override
    public Object visitSumResExpr(MiniLangParser.SumResExprContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        if (left instanceof Integer && right instanceof Integer) {
            int a = (Integer) left;
            int b = (Integer) right;
            return switch (op) {
                case "+" -> a + b;
                case "-" -> a - b;
                default -> throw new RuntimeException("Operador desconocido: " + op);
            };
        }
        double a = ((Number) left).doubleValue();
        double b = ((Number) right).doubleValue();
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            default -> throw new RuntimeException("Operador desconocido: " + op);
        };
    }

    @Override
    public Object visitRelExpr(MiniLangParser.RelExprContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        double a = ((Number) left).doubleValue();
        double b = ((Number) right).doubleValue();
        String op = ctx.op.getText();
        return switch (op) {
            case "<" -> a < b;
            case ">" -> a > b;
            case "<=" -> a <= b;
            case ">=" -> a >= b;
            default -> throw new RuntimeException("Operador desconocido: " + op);
        };
    }

    @Override
    public Object visitIgualdadExpr(MiniLangParser.IgualdadExprContext ctx) {
        Object left = visit(ctx.expr(0));
        Object right = visit(ctx.expr(1));
        String op = ctx.op.getText();
        if (left instanceof Number && right instanceof Number) {
            double a = ((Number) left).doubleValue();
            double b = ((Number) right).doubleValue();
            return switch (op) {
                case "==" -> a == b;
                case "!=" -> a != b;
                default -> throw new RuntimeException("Operador desconocido: " + op);
            };
        }
        return switch (op) {
            case "==" -> left.equals(right);
            case "!=" -> !left.equals(right);
            default -> throw new RuntimeException("Operador desconocido: " + op);
        };
    }

    @Override
    public Object visitAndExpr(MiniLangParser.AndExprContext ctx) {
        boolean left = (boolean) visit(ctx.expr(0));
        boolean right = (boolean) visit(ctx.expr(1));
        return left && right;
    }

    @Override
    public Object visitOrExpr(MiniLangParser.OrExprContext ctx) {
        boolean left = (boolean) visit(ctx.expr(0));
        boolean right = (boolean) visit(ctx.expr(1));
        return left || right;
    }

    @Override
    public Object visitNotExpr(MiniLangParser.NotExprContext ctx) {
        boolean valor = (boolean) visit(ctx.expr());
        return !valor;
    }

    @Override
    public Object visitParenExpr(MiniLangParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitIntLiteral(MiniLangParser.IntLiteralContext ctx) {
        return Integer.valueOf(ctx.getText());
    }

    @Override
    public Object visitFloatLiteral(MiniLangParser.FloatLiteralContext ctx) {
        return Double.valueOf(ctx.getText());
    }

    @Override
    public Object visitBoolLiteral(MiniLangParser.BoolLiteralContext ctx) {
        return Boolean.valueOf(ctx.getText());
    }

    @Override
    public Object visitStringLiteral(MiniLangParser.StringLiteralContext ctx) {
        String texto = ctx.getText();
        return texto.substring(1, texto.length() - 1);
    }

    @Override
    public Object visitVarRef(MiniLangParser.VarRefContext ctx) {
        String nombre = ctx.getText();
        return tabla.obtenerValor(nombre);
    }
}
