package mytest;

public class AuxiliaryClass {
  private String name;
  private int age;
  
  public AuxiliaryClass(String _name, int _age){
    name = _name;
    age = _age;
  }

  @Override
  public String toString() {
    return "AuxiliaryClass [name=" + name + ", age=" + age + "]";
  }

}
