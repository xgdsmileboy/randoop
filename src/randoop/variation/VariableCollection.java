package randoop.variation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import randoop.variation.data.VariableVariant;
import randoop.variation.visitor.AnnotateVisitor;
import randoop.variation.visitor.ClassMappingVisitor;
import randoop.variation.visitor.VariantCollectVisitor;

import mytest.DummyClass;

public class VariableCollection {
  
    private String originalCU;
    
    public VariableCollection(String path){
      
      CompilationUnit cu = parse(readFileToString(path));

      originalCU = cu.toString();
      
//      cu.accept(new AnnotateVisitor());
//      writeStringToFile(path, cu.toString());
      
      cu.accept(new ClassMappingVisitor());
      VariantCollectVisitor vcv = new VariantCollectVisitor(); 
      cu.accept(vcv);
      List<VariableVariant> list = vcv.getVariants();
      
      for(VariableVariant v : list){
        System.out.println(v);
      }
      
      System.out.println("\n\n=======================\n");
      System.out.println(cu.toString());
      System.out.println("\n\n");
      
    }
    
    private CompilationUnit parse(String str) {
      ASTParser parser = ASTParser.newParser(AST.JLS3);
      Map<?,?> options = JavaCore.getOptions();
      JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
      parser.setCompilerOptions(options);
      parser.setSource(str.toCharArray());
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null);
    }
    
    private boolean writeStringToFile(String path, String content){
      
      FileWriter fw = null;
      try {
        fw = new FileWriter(new File(path));
        fw.write(content);
        fw.close();
        return true;
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      return false;
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
//      
//      FileOperate fileOperate = new FileOperate();
//      fileOperate.copyFile(filePath, filePath.substring(0, filePath.lastIndexOf('/'))+"/tmp");
//      
      StringBuilder fileData = new StringBuilder(1000);
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(filePath));
      } catch (FileNotFoundException e1) {
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
    
    
    public static void main(String args[]){
        
      VariableCollection cm = new VariableCollection("E:/Code/Java/Randoop/randoop/src/mytest/DummyClass.java");

//      DummyClass dummyClass = new DummyClass(10, 10);
//      
//      Class clazz = dummyClass.getClass();
//      Method m = null;
//      
//      try {
//        m = clazz.getDeclaredMethod("setSquare", int.class, int.class);
//        m.invoke(dummyClass, -10, -10);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
      
    }
    
}
