package randoop.variation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import randoop.variation.data.ExecutableSequenceWithValues;
import randoop.variation.data.ObjectEntity;
import randoop.variation.data.VariableVariant;

public class MethodMatrix {
  // method name under test
  private String methodName;
  // variables in this method that need to show different values
  private List<VariableVariant> variables = new ArrayList<>();
  // generated executable sequences tests this method, each sequence contains the values of all variables
  private List<ExecutableSequenceWithValues> executableSequenceWithValues = new ArrayList<>();
  // minimized test sequence set
  private Set<ExecutableSequenceWithValues> selectedSequences = new HashSet<>();
  
  public MethodMatrix(String methodName){
    this.methodName = methodName;
  }

  public String getMethodName(){
    return this.methodName;
  }
  
  public void setVariableVaiantList(List<VariableVariant> variableList){
    this.variables = variableList;
  }
  
  public void addVariableVariant(VariableVariant variable){
    this.variables.add(variable);
  }
  
  public void addExecutableSequenceWithValues(ExecutableSequenceWithValues executableSequence){
    if(!executableSequenceWithValues.contains(executableSequence)){
      this.executableSequenceWithValues.add(executableSequence);
    }
  }
  
  public Set<ExecutableSequenceWithValues> getSelectedSequences(){
    Set<ExecutableSequenceWithValues> ssq = null;
    if(filterSequences()){
      ssq = selectedSequences;
    } else {
      ssq = new HashSet<>();
    }
    return ssq;
  }
  
  private boolean filterSequences(){
    
    byte[][] mat = buildMatrix();
    
    // TODO using the Bi-Criteria model to compute the target test sequences
    // after which the selectedSequences is minimized
    
    return true;
  }
  
  private byte[][] buildMatrix(){
    int totalValueNumber = 0;
    
    for(VariableVariant v : variables){
      
      List<ObjectEntity> values = v.getVariantValues();
      
      for(ObjectEntity oe : values){
        oe.setID(totalValueNumber);
        totalValueNumber ++;
      }
      
    }
    
    int totalTestNumber = executableSequenceWithValues.size();
    
    byte[][] mat = new byte[totalTestNumber][totalValueNumber];
    
    // transform the values and test into matrix
    // each column maps to a value, each row maps to a test
    // each cell is either 1 or 0 represents this test can reach this value or not
    for(int i = 0; i < executableSequenceWithValues.size(); i++){
      ExecutableSequenceWithValues es = executableSequenceWithValues.get(i);
      Map<VariableVariant, Object> name2Value = es.getVariableValues();
      
      for(int j = 0; j < variables.size(); j++){
        VariableVariant vv = variables.get(j);
        Object o = name2Value.get(vv);
        assert o != null;
        int id = vv.getValueID(o);
        if(id == -1)
          break;
        mat[i][id] = 1;
      } 
    }
    
    return mat;
  }

}
