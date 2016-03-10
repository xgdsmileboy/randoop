package mytest;

public class DummyClass {
  
  private AuxiliaryClass auxiliaryClass;
  private int a = 9;
  private float b= 10.8f;
  private int[] al = {1,2,3};
  private String[] asl;
  
  public DummyClass(AuxiliaryClass _auxiliaryClass){
    auxiliaryClass = _auxiliaryClass;
    String aString = asl[2];
    String[] tmp = new String[]{"hello"}; 
  }
  
  public void dummy(float a){
    if(a == 10.8){
      System.out.println("error");
    }
  }
  
  
  public void print(){
    System.out.println("auxiliary="+auxiliaryClass.toString());
  }
  
}
