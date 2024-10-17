package nl.han.ica.icss.checker;

import jdk.jfr.Percentage;
import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        loopNodes(ast.root);

    }
    private void loopNodes(ASTNode node) {
        if(node instanceof Stylesheet | node instanceof Stylerule | node instanceof IfClause ) {
            variableTypes.addFirst(new HashMap<>());
        }


        checkVariable(node);
        checkDeclaration(node);
        checkIfStatement(node);

        for (ASTNode child : node.getChildren()) {
            loopNodes(child);
        }

        if(node instanceof Stylesheet | node instanceof Stylerule | node instanceof IfClause ) {
            variableTypes.removeFirst();
        }
    }

    private void checkDeclaration(ASTNode node) {
        if (node instanceof Declaration) {
            Declaration declaration = (Declaration) node;
            PropertyName propertyName = declaration.property;
            Expression expression = declaration.expression;

            if (propertyName.name.equals("width") || propertyName.name.equals("height")) {
                if(expression instanceof VariableReference){
                    VariableReference variableReference = (VariableReference) expression;
                    ExpressionType variableType = getExpressionTypeFromVariableReference(variableReference);
                    if (variableType != ExpressionType.PIXEL && variableType != ExpressionType.PERCENTAGE && variableType != ExpressionType.SCALAR) {
                        ((Declaration) node).expression.setError("Variable in width / height must be a pixel or scalar literal");
                    }
                }
                else if (!(expression instanceof PixelLiteral) && !(expression instanceof Percentage) && !(expression instanceof Operation)) {
                    ((Declaration) node).expression.setError("Width and height must be a pixel or percentage literal");
                }
            }
            if (propertyName.name.equals("color") || propertyName.name.equals("background-color")) {
                if(expression instanceof VariableReference){
                    VariableReference variableReference = (VariableReference) expression;
                    ExpressionType variableType = getExpressionTypeFromVariableReference(variableReference);
                    if (variableType != ExpressionType.COLOR) {
                        ((Declaration) node).expression.setError("Variable in color / background-color must be a color literal");
                    }
                }
                else if (!(expression instanceof ColorLiteral) && !(expression instanceof Operation)) {
                    ((Declaration) node).expression.setError("Color and background-color must be a color literal");
                }
            }
        }
    }

    private void checkVariable(ASTNode variable){
        if(variable instanceof VariableAssignment) {
            VariableAssignment variableAssignment = (VariableAssignment) variable;
            if (variableAssignment.expression != null) {
                ExpressionType expression = getExpressionType(variableAssignment.expression);
                variableTypes.getFirst().put(variableAssignment.name.name, expression);
            }
        }
        if(variable instanceof VariableReference) {
            VariableReference variableReference = (VariableReference) variable;
            ExpressionType expression = getExpressionTypeFromVariableReference(variableReference);
            if (expression == ExpressionType.UNDEFINED) {
                variable.setError("Variabele bestaat niet");
            }
        }
    }

    private ExpressionType getExpressionTypeFromVariableReference(VariableReference variableReference){
        ExpressionType expressionType = ExpressionType.UNDEFINED;
        for (int i = 0; i < variableTypes.getSize(); i++){
            if(variableTypes.get(i).containsKey(variableReference.name)) {
                expressionType = variableTypes.get(i).get(variableReference.name);
                break;
            }
        }
        return expressionType;
    }

    private ExpressionType getExpressionType(Expression expression) {
        if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        }else{
            return ExpressionType.UNDEFINED;
        }
//        else if (expression instanceof VariableReference) {
//            VariableReference variableReference = (VariableReference) expression;
//            Expression newExpression = expression;
//            return getExpressionType(newExpression);
//        }

    }

    private void checkIfStatement(ASTNode node) {
        if (node instanceof IfClause) {
            IfClause ifClause = (IfClause) node;

            ExpressionType expressionType;
            if(ifClause.conditionalExpression instanceof VariableReference){
                VariableReference variableReference = (VariableReference) ifClause.conditionalExpression;
                expressionType = getExpressionTypeFromVariableReference(variableReference);
            }else{
                expressionType = getExpressionType(ifClause.conditionalExpression);
            }

            if (expressionType != ExpressionType.BOOL) {
                ifClause.conditionalExpression.setError("If statement expression must be a boolean");
            }
        }
    }
    
}
