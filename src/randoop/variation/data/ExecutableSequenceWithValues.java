package randoop.variation.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import randoop.sequence.ExecutableSequence;

public class ExecutableSequenceWithValues {
  // executable sequence used to composite test method/input
  private ExecutableSequence executableSequence;
  // the list of variable names of which we hope to capture the values 
  // when running the sequence 
  private List variableNames;
  // map the values to the variable names
  private Map<VariableVariant, Object> variable2Value = new HashMap<>();
  
  public ExecutableSequenceWithValues(ExecutableSequence es, List vnames){
    executableSequence = es;
    variableNames = vnames;
    
    computeVariableValues();
  }
  
  public ExecutableSequence getExecutableSequence(){
    return executableSequence;
  }
  
  private void computeVariableValues(){
    //TODO compute the value of each variable in variableNameList in the runtime
    
  }
  
  public Map<VariableVariant, Object> getVariableValues(){
    return variable2Value;
  }
  
}
