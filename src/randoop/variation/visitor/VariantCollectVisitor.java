package randoop.variation.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import randoop.variation.VariableExtraction;
import randoop.variation.data.VariableVariant;


public class VariantCollectVisitor extends ASTVisitor {
 
  
  private List<VariableVariant> variants = new ArrayList<>();
  
  public List<VariableVariant> getVariants(){
    return variants;
  }
 
  public boolean visit(Assignment node){
    
//    System.out.println("Assignment ->"+node);
    return true;
  }
  
  
  public boolean visit(AssertStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));
    
//    System.out.println("AssertStatement ->"+node);
    return true;
  }
  
  public boolean visit(DoStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));

//    System.out.println("DoStatement ->"+node);
    return true;
  }
  
  public boolean visit(ExpressionStatement node){
    
    Expression exp = node.getExpression();
    
    
    if(exp instanceof Annotation){
      
      
    } else if(exp instanceof ArrayAccess){
      variants.addAll(VariableExtraction.extract((ArrayAccess)exp));
      
    } else if(exp instanceof ArrayInitializer){
      
      
    } else if(exp instanceof Assignment){
      
      
    } else if(exp instanceof BooleanLiteral){
      
      
    } else if(exp instanceof CastExpression){
      
      
    } else if(exp instanceof CharacterLiteral){
      
      
    } else if(exp instanceof ClassInstanceCreation){
      
      
    } else if(exp instanceof ConditionalExpression){
      variants.addAll(VariableExtraction.extract((ConditionalExpression)exp));
      
    } else if(exp instanceof FieldAccess){
      
      
    } else if(exp instanceof InfixExpression){
      
      
    } else if(exp instanceof InstanceofExpression){
      
      
    } else if(exp instanceof MethodInvocation){

      
    } else if(exp instanceof Name){
      
      
    } else if(exp instanceof NullLiteral){
      
      
    } else if(exp instanceof NumberLiteral){
      
      
    } else if(exp instanceof ParenthesizedExpression){
      
      
    } else if(exp instanceof PostfixExpression){
      
      
    } else if(exp instanceof StringLiteral){
      
      
    } else if(exp instanceof PrefixExpression){
      
      
    } else if(exp instanceof SuperMethodInvocation){
      
      
    } else if(exp instanceof ThisExpression){
      
      
    } else if(exp instanceof TypeLiteral){
      
      
    } else if(exp instanceof VariableDeclarationExpression){
      
    } else {
      System.out.println("StatementVisitor @228 error: No expression matched to \""+exp+"\"");
    }  
    
//    System.out.println("ExpressionStatement ->"+node);
    return true;
  }
  
  public boolean visit(ForStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));
//    System.out.println("ForStatement ->"+node);
    return true;
  }
  
  public boolean visit(IfStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));
//    System.out.println("IfStatement ->"+node);
    return true;
  }
  
  public boolean visit(LabeledStatement node){

//    System.out.println("LabeledStatement ->"+node);
    return true;
  }
  
  public boolean visit(ReturnStatement node){

//    System.out.println("ReturnStatement ->"+node);
    return true;
  }
  
  public boolean visit(SuperConstructorInvocation node){

//    System.out.println("SuperConstructorInvocation ->"+node);
    return true;
  }
  
  public boolean visit(SwitchCase node){
    
//    System.out.println("SwitchCase ->"+node);
    return true;
  }
  
  public boolean visit(SwitchStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));
//    System.out.println("SwitchStatement ->"+node);
    return true;
  }
  
  public boolean visit(SynchronizedStatement node){

//    System.out.println("SynchronizedStatement ->"+node);
    return true;
  }
  
  public boolean visit(ThrowStatement node){
    
//    System.out.println("ThrowStatement ->"+node);
    return true;
  }
  
  public boolean visit(TryStatement node){
   
//    System.out.println("TryStatement ->"+node);
    return true;
  }
  
  public boolean visit(WhileStatement node){
    variants.addAll(VariableExtraction.extract(node.getExpression()));
//    System.out.println("WhileStatement ->"+node);
    return true;
  }

}
