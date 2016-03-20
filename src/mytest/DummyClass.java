package mytest;

public class DummyClass {
  
  private int a;
  private int b;

  
  public DummyClass(int _a, int _b){
    a= _a;
    b = _b;
  }
  
  
  public int getArea(){
    return a*b;
  }
  
  public void setSquare(int _a, int _b){
    a = _a;
    b = _b;
  }
  
  public int getA(){
    return a;
  }
  
  
}
