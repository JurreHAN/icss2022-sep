package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
         variableTypes = new HANLinkedList<>();
         loopNodes(ast.root);

    }
    private void loopNodes(ASTNode node) {
        for (ASTNode child : node.getChildren()) {
//            checkVariable(child);
            checkDeclaration(child);

            loopNodes(child);
        }
    }

    private void checkDeclaration(ASTNode node) {
        if (node instanceof Declaration) {
            Declaration declaration = (Declaration) node;
            PropertyName propertyName = declaration.property;
            Expression expression = declaration.expression;

            if (propertyName.name.equals("width") || propertyName.name.equals("height")) {
                if(expression instanceof VariableReference){
                    System.out.println("VariableReference");
                } else if (!(expression instanceof PixelLiteral)) {
                    System.out.println("Not a PixelLiteral");
                }
            }
        }
    }

//    private void checkVariable (ASTNode node) {
//        if (node instanceof VariableAssignment) {
//            String variableName = ((VariableAssignment) node).name.name;
//            if (isVariableInScope(variableName)) {
//                throw new RuntimeException("Variable " + variableName + " is used outside of its scope");
//            }
//        }
//    }
//
//    private boolean isVariableInScope(String variableName) {
//        for (HashMap<String, ExpressionType> scope : variableTypes) {
//            if (scope.containsKey(variableName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    
}
