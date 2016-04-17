package randoop.variation.data;

public enum OperationType {

  /** bit operators      */
  // '&' operator
  AND,
  // '|' operator
  OR,
  // '^'
  XOR,
  //'<<' operator
  LETF_SHIFT,  
  // '>>' 
  RIGHT_SHIFT_SIGNED,
  // '>>>'
  RIGHT_SHIFT_UNSIGNED,

  
  /**  logical oprators   */
  // '>' operator
  GREATER,
  // '<' operator
  LESS,
  // '>=' operator
  GREATER_EQUALS,
  // '<=' operator
  LESS_EQUALS,
  //'==' operator
  EQUALS,
  // '!=' operator
  NOT_EQUALS,
  // '&&' operator
  CONDITIONAL_AND,
  // '||" operator
  CONDITIONAL_OR,
  
  /** arithmetic operators   */
  // '*'
  TIMES,
  // '/' operator
  DIVIDE,
  // '-' operator
  MINUS,
  // '+' operator, the operand can be string
  PLUS,
  // '%' operator
  REMINDER,
  
  /** some other useful operations    */
  ARRAY_ACCESS,
  
  /** default   */
  //default
  UNKOWN
}
