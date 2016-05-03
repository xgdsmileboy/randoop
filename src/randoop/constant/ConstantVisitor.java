package randoop.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;


public class ConstantVisitor extends ASTVisitor {
  
//  private List<Object> constants = new ArrayList<Object>();
  private Set<Object> constants = new HashSet<>();
  
  private HashMap<String, Object> objMap = new HashMap<String, Object>();
  
  public Set getConstantsList(){
    return constants;
  }
  
  public boolean visit(Assignment node){
    Expression lhand = node.getLeftHandSide();
    Expression rhand = node.getRightHandSide();
    
    return true;
  }
  
  public boolean visit(NumberLiteral node){
    String value = node.toString();
    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
    if(pattern.matcher(value).matches()){
      constants.add(Integer.valueOf(value).intValue());
    }else if(value.endsWith("f") || value.endsWith("F")) {
      constants.add(Float.valueOf(value.substring(0, value.length()-1)).floatValue());
    }else if(value.endsWith("l") || value.endsWith("L")){
      constants.add(Long.parseLong(value.substring(0, value.length()-1)));
    }else {
      constants.add(Double.valueOf(value).doubleValue());
    }
    return true;
  }
  
  public boolean visit(StringLiteral node){
    constants.add(node.toString());
    return true;
  }
}