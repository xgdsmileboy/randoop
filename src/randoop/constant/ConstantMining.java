package randoop.constant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ConstantMining {
    private ConstantVisitor constantVisitor;
    
    public ConstantMining(String path){
      
  //    String path = "/Users/Jiajun/Code/Java/grt/randoop/src/mytest/DummyClass.java";
      CompilationUnit cu = parse(readFileToString(path));
      
      constantVisitor = new ConstantVisitor();
      
      cu.accept(constantVisitor);
      
      
    }
    
    public Set getSeedsSet(){
      return constantVisitor.getConstantsList();
    }
    
    private CompilationUnit parse(String str) {
      ASTParser parser = ASTParser.newParser(AST.JLS3);
      parser.setSource(str.toCharArray());
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
   
      CompilationUnit cu = (CompilationUnit) parser.createAST(null);
      
      return cu;
    }
    
   
    //read file content into a string
    private String readFileToString(String filePath){
      
      if(null == filePath){
        System.out.println("ConstantMining @47 test file path: "+filePath);
        System.exit(1);
      }
      
      if(!filePath.endsWith(".java")){
        filePath += ".java";
      }
      
      int index = filePath.lastIndexOf('.');
      filePath = filePath.substring(0, index).replace('.', '/')+filePath.substring(index);
      
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
      try{
      while ((numRead = reader.read(buf)) != -1) {
        String readData = String.valueOf(buf, 0, numRead);
        fileData.append(readData);
        buf = new char[1024];
      }
   
      reader.close();
      }catch (Exception e ){
        e.printStackTrace();
      }
   
      return  fileData.toString();  
    }
    
}