package randoop.variation.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import randoop.variation.data.Utils;

public class ClassMappingVisitor extends ASTVisitor {

  public boolean visit(TypeDeclaration node) {

    FieldDeclaration fields[] = node.getFields();
    for (FieldDeclaration f : fields) {
      Class clazz = Utils.convert2Class(f.getType());
      for (Object o : f.fragments()) {
        VariableDeclarationFragment vdf = (VariableDeclarationFragment) o;
        Utils.addFieldClass(vdf.getName().toString(), clazz);
      }
    }
    return true;
  }

  public boolean visit(MethodDeclaration node) {

    String methodName = node.getName().toString();
    Map<String, Class> map = new HashMap<>();
    for (Object o : node.parameters()) {
      SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
      Utils.addMethodVariableClass(methodName, svd.getName().toString(), Utils.convert2Class(svd.getType()));
    }

    MethodVisitor mv = new MethodVisitor();
    node.accept(mv);
    
    for(Entry<String, Class> entry : mv.getVarMap().entrySet()){
      Utils.addMethodVariableClass(methodName, entry.getKey(), entry.getValue());
    }
    
//    System.out.println("MethodDeclaration = " + node);
    return true;
  }

  class MethodVisitor extends ASTVisitor {

    Map<String, Class> map = new HashMap<>();

    public Map<String, Class> getVarMap() {
      return map;
    }

    public boolean visit(ConditionalExpression node) {

      System.out.println("ConditionalExpression -->" + node);
      return true;
    }

    public boolean visit(InfixExpression node) {
      System.out.println("InfixExpression -->" + node);
      return true;
    }

    public boolean visit(InstanceofExpression node) {
      System.out.println("InstanceofExpression -->" + node);
      return true;
    }

    public boolean visit(MethodInvocation node) {
      System.out.println("MethodInvocation -->" + node);
      return true;
    }

    public boolean visit(Name node) {
      System.out.println("Name -->" + node);
      return true;
    }

    public boolean visit(ParenthesizedExpression node) {
      System.out.println("ParenthesizedExpression -->" + node);
      return true;
    }

    public boolean visit(PostfixExpression node) {
      System.out.println("PostfixExpression -->" + node);
      return true;
    }

    public boolean visit(PrefixExpression node) {
      System.out.println("PrefixExpression -->" + node);
      return true;
    }

    public boolean visit(TypeLiteral node) {
      System.out.println("TypeLiteral -->" + node);
      return true;
    }

    public boolean visit(VariableDeclarationStatement node) {

      Class clazz = Utils.convert2Class(node.getType());
      for (Object o : node.fragments()) {
        VariableDeclarationFragment vdf = (VariableDeclarationFragment) o;
        map.put(vdf.getName().toString(), clazz);
      }

      System.out.println("VariableDeclarationExpression -->" + node);
      return true;
    }
  }

}
