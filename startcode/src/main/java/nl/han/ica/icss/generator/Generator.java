package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;

public class Generator {

	public String generate(AST ast) {
		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < ast.root.getChildren().size(); i++) {
			returnString.append(getStyleruleString(ast.root.getChildren().get(i)));
		}
        return returnString.toString();
	}

	public String getStyleruleString(ASTNode astNode) {
		StringBuilder returnString = new StringBuilder();
		Stylerule stylerule = (Stylerule) astNode;
		returnString.append(stylerule.selectors.get(0).toString());
		returnString.append(" {\n");
		for (int i = 0; i < stylerule.body.size(); i++) {
			Declaration declaration = (Declaration) stylerule.body.get(i);
			returnString.append("\t");
			returnString.append(declaration.property.name);
			returnString.append(": ");
			returnString.append(getValueStringFromExpression(declaration.expression));
			returnString.append(";\n");
		}
		returnString.append("}\n");
		return returnString.toString();
	}

	public String getValueStringFromExpression(Expression expression) {
		if (expression instanceof PixelLiteral) {
			return ((PixelLiteral) expression).value + "px";
		} else if (expression instanceof PercentageLiteral) {
			return ((PercentageLiteral) expression).value + "%";
		} else if (expression instanceof ScalarLiteral) {
			return ((ScalarLiteral) expression).value + "";
		} else if (expression instanceof ColorLiteral) {
			return ((ColorLiteral) expression).value;
		} else {
			return "";
		}
	}
	
}
