package randoop.variation.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class AnnotateVisitor extends ASTVisitor {
  
  public AnnotateVisitor(){
    System.out.println("AnnotateVisitor : ");
  }
  
  public boolean visit(Block node) {
    
    List<Statement> lst = node.statements();
    List<Statement> reLst = new ArrayList();
    for (Statement s : lst) {
      if (s instanceof IfStatement) {
        IfStatement ifStatement = (IfStatement) s;
        Expression e = ifStatement.getExpression();
        ExpressionStatement es = genExpressionStatement(node.getAST(), e.toString());
        reLst.add(es);
      } else if (s instanceof WhileStatement){
        WhileStatement whileStatement = (WhileStatement) s;
        Expression e = whileStatement.getExpression();
        ExpressionStatement es = genExpressionStatement(node.getAST(), e.toString());
        reLst.add(es);
      } else if(s instanceof DoStatement){
        DoStatement doStatement = (DoStatement) s;
        Expression e = doStatement.getExpression();
        ExpressionStatement es = genExpressionStatement(node.getAST(), e.toString());
        reLst.add(es);
      } else if (s instanceof SwitchStatement){
        SwitchStatement switchStatement = (SwitchStatement) s;
        Expression e = switchStatement.getExpression();
        ExpressionStatement es = genExpressionStatement(node.getAST(), e.toString());
        reLst.add(es);
      }else if (s instanceof ExpressionStatement) {
        ExpressionStatement es = (ExpressionStatement) s;
        Expression e = es.getExpression();
        if (e instanceof MethodInvocation) {
          MethodInvocation m = (MethodInvocation) e;
          // delete auxiliary output statement "System,out.println("$$**")"
          if (m.getName().toString().equals("println") && m.arguments().get(0).toString().replace("\"", "").startsWith("$$")) {
            continue;
          }
        }
      }
      reLst.add(s);
    }

    node.statements().clear();

    for (Statement s : reLst) {
      node.statements().add(ASTNode.copySubtree(node.getAST(), s));
    }

    System.out.println("Block ->" + node);
//    node.accept(new VarCollectVisitor());
    return true;
  }
  
  private ExpressionStatement genExpressionStatement(AST ast, String message){
    MethodInvocation mi = ast.newMethodInvocation();
    SimpleName sn1 = ast.newSimpleName("System");
    SimpleName sn2 = ast.newSimpleName("out");
    QualifiedName qfn = ast.newQualifiedName(sn1, sn2);
    mi.setExpression(qfn);
    mi.setName(ast.newSimpleName("println"));
    StringLiteral sl = ast.newStringLiteral();
    sl.setLiteralValue("$$"+message);
    mi.arguments().add(sl);
    ExpressionStatement es = ast.newExpressionStatement(mi);
    return es;
  }
  

}
