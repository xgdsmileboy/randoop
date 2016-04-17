package randoop.variation.data;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.Type;

public class Utils {
  
  private static Map<String, Class> fieldTypeMap = new HashMap<>();
  private static Map<String, Map<String, Class>> localTypeMap = new HashMap<>();
  
  public static boolean addFieldClass(String fieldName, Class clazz){
    if(fieldTypeMap.containsKey(fieldName) && fieldTypeMap.get(fieldName) != clazz){
      System.out.println("Field type inconsistancy '"+fieldName+"' with types : "+fieldTypeMap.get(fieldName) +" and "+clazz);
      return false;
    }
    fieldTypeMap.put(fieldName, clazz);
    return true;
  }
  
  public static boolean addMethodVariableClass(String methodName, String varName, Class clazz){
    if(!localTypeMap.containsKey(methodName)){
      Map<String, Class> map = new HashMap<>();
      map.put(varName, clazz);
      localTypeMap.put(methodName, map);
      return true;
    } else {
      Map<String, Class> map = localTypeMap.get(methodName);
      if(map.containsKey(varName) && map.get(varName) != clazz){
        System.out.println("Variable type inconsistancy of '"+varName+"' in method '"+methodName+"' with types : "+map.get(varName)+" and "+clazz);
        return false;
      }
      map.put(varName, clazz);
      return true;
    }
  }
  
  public static Class getVariableClass(String methodName, String varName){
    if (localTypeMap.containsKey(methodName) && localTypeMap.get(methodName).get(varName) != null) {
      return localTypeMap.get(methodName).get(varName);
    } else {
      return fieldTypeMap.get(varName);
    }
  }
  
  
  public static Class convert2Class(Type type){
    switch(type.toString()){
      case "void" : return void.class;
      case "int" : return int.class;
      case "char" : return char.class;
      case "short" : return short.class;
      case "long" : return long.class;
      case "float" : return float.class;
      case "double" : return double.class;
      case "byte" : return byte.class;
        default: 
    }
    return null;
  }
  
}

