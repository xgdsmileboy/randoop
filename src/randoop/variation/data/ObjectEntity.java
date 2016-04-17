package randoop.variation.data;

public class ObjectEntity {
  // object value
  private Object value;
  // value id that will be used in the matrix
  private int id;
  
  public ObjectEntity(Object o){
    value = o;
  }
  
  public void setID(int id){
    this.id = id;
  }
  
  public int getID(){
    return this.id;
  }
  
}
