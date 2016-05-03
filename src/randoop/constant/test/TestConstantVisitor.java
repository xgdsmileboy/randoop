package randoop.constant.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import randoop.constant.ConstantVisitor;

public class TestConstantVisitor {

  public static CompilationUnit parse(String str) {
    ASTParser parser = ASTParser.newParser(AST.JLS3);
    parser.setSource(str.toCharArray());
    parser.setKind(ASTParser.K_COMPILATION_UNIT);

    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    
    return cu;
  }

  // read file content into a string
  public static String readFileToString(String filePath) {

    int index = filePath.lastIndexOf('.');
    filePath = filePath.substring(0, index).replace('.', '/') + filePath.substring(index);

    StringBuilder fileData = new StringBuilder(1000);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filePath));
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    char[] buf = new char[10];
    int numRead = 0;
    try {
      while ((numRead = reader.read(buf)) != -1) {
        String readData = String.valueOf(buf, 0, numRead);
        fileData.append(readData);
        buf = new char[1024];
      }

      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return fileData.toString();
  }

  public static void main(String[] args) {

    String path = "src.mytest.DummyClass.java";
    CompilationUnit cu = parse(readFileToString(path));

    cu.accept(new ConstantVisitor());
  }
}