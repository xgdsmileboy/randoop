package randoop.variation.test;

public class TestRuntime {
  public static void main(String args[]){
    Process p = null;
    
    byte[][] a = new byte[4][4];
    for(int i = 0; i < a.length; i++){
      for(int j = 0; j < a[0].length; j++){
        System.out.print(a[i][j]+" ");
      }
      System.out.println();
    }
    
    try {
      Runtime run = Runtime.getRuntime();
      p = run.exec("System.out.println(\"hello\");");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
