package randoop.variation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.StringLiteral;

import randoop.variation.data.OperationType;
import randoop.variation.data.Utils;
import randoop.variation.data.VariableVariant;


public class VariableExtraction {

  public static List<VariableVariant> extract(Expression expression) {
    List<VariableVariant> variables = new ArrayList<>();
    if (expression instanceof InfixExpression) {
      variables = extract((InfixExpression) expression);
    } else if(expression instanceof ArrayAccess){
      variables = extract((ArrayAccess) expression);
    }

    return variables;
  }

  private static List<VariableVariant> extract(ArrayAccess expression){
    List<VariableVariant> variables = new ArrayList<>();
    
//    System.out.println("ArrayAccess : "+expression);
    
    return variables;
  }
  
  
  private static List<VariableVariant> extract(InfixExpression expression) {
    List<VariableVariant> variables = new ArrayList<>();

    Expression leftExp = expression.getLeftOperand();
    Expression rightExp = expression.getRightOperand();
    Operator operator = expression.getOperator();
    OperationType opty = OperationType.UNKOWN;
    
    if(leftExp instanceof FieldAccess){
      leftExp = ((FieldAccess)leftExp).getName();
    }
    if(rightExp instanceof FieldAccess){
      rightExp = ((FieldAccess)rightExp).getName();
    }

    if (InfixExpression.Operator.AND.equals(operator)) {
      opty = OperationType.AND;

    } else if (InfixExpression.Operator.OR.equals(operator)) {
      opty = OperationType.OR;

    } else if (InfixExpression.Operator.XOR.equals(operator)) {
      opty = OperationType.XOR;

    } else if (InfixExpression.Operator.LEFT_SHIFT.equals(operator)) {
      opty = OperationType.LETF_SHIFT;

    } else if (InfixExpression.Operator.RIGHT_SHIFT_SIGNED.equals(operator)) {
      opty = OperationType.RIGHT_SHIFT_SIGNED;

    } else if (InfixExpression.Operator.RIGHT_SHIFT_UNSIGNED.equals(operator)) {
      opty = OperationType.RIGHT_SHIFT_UNSIGNED;

    } else if (InfixExpression.Operator.GREATER.equals(operator)) {
      opty = OperationType.GREATER;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
        System.out.println("TODO: extract =>"+expression);
        
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      } 

    } else if (InfixExpression.Operator.LESS.equals(operator)) {
      opty = OperationType.LESS;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
        
        System.out.println("TODO: extract expression =>"+expression);
        
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      }

    } else if (InfixExpression.Operator.GREATER_EQUALS.equals(operator)) {
      opty = OperationType.GREATER_EQUALS;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
        System.out.println("TODO: extract expression =>"+expression);
        
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      } 
      
    } else if (InfixExpression.Operator.LESS_EQUALS.equals(operator)) {
      opty = OperationType.LESS_EQUALS;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
        System.out.println("TODO: extract expression =>"+expression);
        
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      } 
      
    } else if (InfixExpression.Operator.EQUALS.equals(operator)) {
      opty = OperationType.EQUALS;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      } 

    } else if (InfixExpression.Operator.NOT_EQUALS.equals(operator)) {
      opty = OperationType.NOT_EQUALS;
      Object value = null;
      if (leftExp instanceof Name && isLiteral(rightExp)) {
        Name var = (Name) leftExp;
        value = getLiteralValue(rightExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else if (rightExp instanceof Name && isLiteral(leftExp)) {
        Name var = (Name) rightExp;
        value = getLiteralValue(leftExp);
        VariableVariant vv = VariableVariant.newInstance(getExpressionClass(var), var.toString(), var.getStartPosition(), opty, value);
        variables.add(vv);

      } else {
        // TODO
        System.out.println("TODO: extract expression =>"+expression);
        
//        extract(leftExp, visitor);
//        extract(rightExp, visitor);
      } 

    } else if (InfixExpression.Operator.CONDITIONAL_AND.equals(operator)) {
      opty = OperationType.CONDITIONAL_AND;
      variables.addAll(extract(leftExp));
      variables.addAll(extract(rightExp));

    } else if (InfixExpression.Operator.CONDITIONAL_OR.equals(operator)) {
      opty = OperationType.CONDITIONAL_OR;
      variables.addAll(extract(leftExp));
      variables.addAll(extract(rightExp));

    } else if (InfixExpression.Operator.TIMES.equals(operator)) {
      opty = OperationType.TIMES;

    } else if (InfixExpression.Operator.DIVIDE.equals(operator)) {
      opty = OperationType.DIVIDE;

    } else if (InfixExpression.Operator.MINUS.equals(operator)) {
      opty = OperationType.MINUS;

    } else if (InfixExpression.Operator.PLUS.equals(operator)) {
      opty = OperationType.PLUS;

    } else if (InfixExpression.Operator.REMAINDER.equals(operator)) {
      opty = OperationType.REMINDER;

    }

    return variables;
  }

  private static Class getExpressionClass(Name expression) {
    Class clazz = null;
    ASTNode node = expression;
    while (node != null && !(node instanceof MethodDeclaration))
      node = node.getParent();

    if (node == null)
      return null;

    MethodDeclaration md = (MethodDeclaration) node;

    return Utils.getVariableClass(md.getName().toString(), expression.toString());
  }

  private static Object getExpressionNumberValue(NumberLiteral literal) {
    String valueToken = literal.getToken();
    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    if (pattern.matcher(valueToken).matches()) {
      return Integer.parseInt(valueToken);
    } else if (valueToken.endsWith("f")) {
      return Float.parseFloat(valueToken);
    }

    return Double.parseDouble(valueToken);

  }

  private static boolean isLiteral(Expression expression) {

    return (expression instanceof NullLiteral) || (expression instanceof NumberLiteral) || (expression instanceof StringLiteral)
        || (expression instanceof BooleanLiteral) || (expression instanceof CharacterLiteral);
  }

  private static Object getLiteralValue(Expression expression) {
    Object value = null;
    
    assert isLiteral(expression);
    
    if (expression instanceof NullLiteral) {
      value = null;
    } else if (expression instanceof NumberLiteral) {
      value = getExpressionNumberValue((NumberLiteral) expression);
    } else if (expression instanceof StringLiteral) {
      value = ((StringLiteral) expression).getLiteralValue();
    } else if (expression instanceof BooleanLiteral){
      value = ((BooleanLiteral)expression).booleanValue();
    } else if (expression instanceof CharacterLiteral){
      value = ((CharacterLiteral)expression).charValue();
    }
    return value;
  }

}
