package nl.han.ica.icss.parser;

import java.util.Stack;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

	@Override public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		currentContainer.push(stylesheet);
	}
	@Override public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
		ast.setRoot(stylesheet);
	}
	@Override public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		Stylerule stylerule = new Stylerule();
		currentContainer.push(stylerule);
	}
	@Override public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		Stylerule stylerule = (Stylerule) currentContainer.pop();
		currentContainer.peek().addChild(stylerule);
	}
	@Override public void enterTagselector(ICSSParser.TagselectorContext ctx) {
		Selector selector;
		if(ctx.getText().startsWith("#")){
			selector = new IdSelector(ctx.getText());
		} else if (ctx.getText().startsWith(".")) {
			selector = new ClassSelector(ctx.getText());
		}else{
			selector = new TagSelector(ctx.getText());
		}

		currentContainer.push(selector);
	}
	@Override public void exitTagselector(ICSSParser.TagselectorContext ctx) {
		Selector selector = (Selector) currentContainer.pop();
		currentContainer.peek().addChild(selector);
	}

	@Override public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.push(declaration);
	}
	@Override public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = (Declaration) currentContainer.pop();
		currentContainer.peek().addChild(declaration);
	}
	@Override public void enterLiteralValue(ICSSParser.LiteralValueContext ctx) {
		Literal literal;
		if (ctx.getText().startsWith("#")) {
			literal = new ColorLiteral(ctx.getText());
		} else if (ctx.getText().endsWith("px")) {
			literal = new PixelLiteral(ctx.getText());
		} else if (ctx.getText().endsWith("%")) {
			literal = new PercentageLiteral(ctx.getText());
		} else if (ctx.getText().equals("TRUE") || ctx.getText().equals("FALSE")){
			literal = new BoolLiteral(ctx.getText());
		}else {
			literal = new ScalarLiteral(ctx.getText());
		}
		currentContainer.peek().addChild(literal);

	}

//	@Override public void exitLiteralValue(ICSSParser.LiteralValueContext ctx) { }

//	@Override public void enterStyleOption(ICSSParser.StyleOptionContext ctx) { }
//	@Override public void exitStyleOption(ICSSParser.StyleOptionContext ctx) { }
//	@Override public void exitPropertyValue(ICSSParser.PropertyValueContext ctx) { }
	@Override public void enterProperty(ICSSParser.PropertyContext ctx) {
		PropertyName propertyName = new PropertyName(ctx.getText());
		currentContainer.peek().addChild(propertyName);
	}
//	@Override public void exitProperty(ICSSParser.PropertyContext ctx) {
//
//	}

	@Override public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		VariableAssignment variableAssignment = new VariableAssignment();
		currentContainer.push(variableAssignment);
	}

	@Override public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();
		currentContainer.peek().addChild(variableAssignment);
	}
//
	@Override public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		VariableReference variableReference = new VariableReference(ctx.getText());
		currentContainer.peek().addChild(variableReference);
	}

//	@Override public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
//	}

//
	@Override public void enterExpression(ICSSParser.ExpressionContext ctx) {
		if (ctx.getChildCount() == 3) {
			Operation operation;
			switch (ctx.getChild(1).getText()) {
				case "*":
					operation = new MultiplyOperation();
					break;
				case "+":
					operation = new AddOperation();
					break;
				default:
					operation = new SubtractOperation();
			}
			currentContainer.push(operation);
		}

	}
////
	@Override public void exitExpression(ICSSParser.ExpressionContext ctx) {
		if (ctx.PLUS() != null || ctx.MIN() != null || ctx.MUL() != null) {
			Operation operation = (Operation) currentContainer.pop();
			currentContainer.peek().addChild(operation);
		}
	}

	@Override public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause ifClause = new IfClause();
		currentContainer.push(ifClause);

	}

	@Override public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause ifClause = (IfClause) currentContainer.pop();
		currentContainer.peek().addChild(ifClause);
	}

	@Override public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
		ElseClause elseClause = new ElseClause();
		currentContainer.push(elseClause);
	}

	@Override public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
		ElseClause elseClause = (ElseClause) currentContainer.pop();
		currentContainer.peek().addChild(elseClause);
	}

//	@Override public void enterIfExpression(ICSSParser.IfExpressionContext ctx) { }
//	@Override public void exitIfExpression(ICSSParser.IfExpressionContext ctx) { }

	@Override public void enterBooleanValue(ICSSParser.BooleanValueContext ctx) {
		BoolLiteral boolLiteral = new BoolLiteral(ctx.getText());
		currentContainer.peek().addChild(boolLiteral);
	}

}