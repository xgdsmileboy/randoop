package randoop.variation.data;

import java.util.ArrayList;
import java.util.List;

public class VariableVariant {
  // record the position of the variable (sometimes, the variable can appear at
  // more than one position, but we just want it show different value at the
  // specific position, not all the positions)
  private int position;
  // record the name of the variable
  private String name;
  // record the type of the variable
  private Class<?> clazz;
  // record the operation type related to the variable, which will be used
  // when produce different values for it
  private OperationType operationType;
  // record the original value(such at "a > 5", the value 5 will be stored)
  // of the variable in the operation statement
  private Object originalValue;
  // the produced value list, which contains all the values that we hope
  // the test input can make this variable show
  private List<ObjectEntity> valueLst;
  // default instance, avoid error
  private final static VariableVariant instance = new VariableVariant(Object.class, "DummyName", -1, OperationType.UNKOWN, null);
  
  private VariableVariant(Class<?> _clazz, String _name, int position, OperationType _operationType, Object _value) {
    this.clazz = _clazz;
    this.name = _name;
    this.position = position;
    this.operationType = _operationType;
    this.originalValue = _value;
    this.valueLst = new ArrayList<>();
    computeMutateValues();
  }

  public static VariableVariant newInstance(Class<?> _clazz, String _name, int position, OperationType _operationType, Object _value) {
    VariableVariant vr = null;
    if (_clazz == null || _name == null)
      vr = VariableVariant.instance;
    else {
      vr = new VariableVariant(_clazz, _name, position, _operationType, _value);
    }
    return vr;
  }

  public int getNumberofValues() {
    return valueLst.size();
  }

  public Class<?> getType() {
    return clazz;
  }

  public String getName() {
    return name;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public List<ObjectEntity> getVariantValues() {
    return valueLst;
  }

  public Object getOriginalValue() {
    return originalValue;
  }
  
  /**
   * This is the core function of the class
   */
  private void computeMutateValues(){
    
    //TODO compute the optional values that we want to pick for this variable
    
  }

  public boolean addValue(Object value) {
    ObjectEntity oe = new ObjectEntity(value);
    if (!value.getClass().equals(clazz)) {
      return false;
    } else if (!valueLst.contains(oe)) {
      valueLst.add(oe);
    }
    return true;
  }
  
  public int getValueID(Object o){
    for(ObjectEntity oe : valueLst){
      if(oe.equals(o)){
        return oe.getID();
      }
    }
    return -1;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VariableVariant other = (VariableVariant) obj;
    if (clazz == null) {
      if (other.clazz != null)
        return false;
    } else if (!clazz.equals(other.clazz))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (operationType != other.operationType)
      return false;
    if (originalValue == null) {
      if (other.originalValue != null)
        return false;
    } else if (!originalValue.equals(other.originalValue))
      return false;
    if (position != other.position)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "VariableVariant [position=" + position + ", name=" + name + ", clazz=" + clazz + ", operationType=" + operationType + ", originalValue="
        + originalValue + ", valueLst=" + valueLst +  "]";
  }



}
