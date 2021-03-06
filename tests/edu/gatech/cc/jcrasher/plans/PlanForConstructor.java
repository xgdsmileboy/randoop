/*
 * PlanForFunction.java
 * 
 * Copyright 2002 Christoph Csallner and Yannis Smaragdakis.
 */
package edu.gatech.cc.jcrasher.plans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import edu.gatech.cc.jcrasher.writer.CodeGenFct;

/**
 * PlanForFunction
 *
 * Wraps a constructor
 * - Manages recursion to params of constructor
 *
 * Automatic Testing: 
 * Crash java classes by passing inconvenient params
 * 
 * Christoph Csallner
 * 2002-06-12 created
 */
public class PlanForConstructor implements PlanForFunction {

	/**
	 * Type of instance generated by this plan.
	 */
	private Class<?> returnType = null;
 
	/**
	 * Which plans generate params passed to construct this value
	 * - zero-elem-array --> constructor takes no arguments
	 * - list of params in order to construct value
	 */
	private Plan[] constrParams = null;

	/**
	 * How to instantiate an enclosing instance e, on which we can do e.new X
	 * - null iff not an inner class
	 */
	private Plan enclosedBy = null;

	/**
	 * Which constructing function has generated this value?
	 */
	private Constructor<?> conCons = null;


	/***********************************************************************
 	 * Constructor
	 * - not an inner class
	 */
	public PlanForConstructor(
		Constructor<?> pConstructor, 
  		Plan[] pConstrParams) {

  		assert pConstructor != null;
  		assert pConstrParams != null;
  		
  		returnType = pConstructor.getDeclaringClass();
  		conCons = pConstructor;
  		constrParams = pConstrParams;
	}

	/**
	 * Constructor 
	 * - an inner class
	 * 
	 * never null enclosing type
	 */
	public PlanForConstructor(
		Constructor<?> pConstructor, 
  		Plan[] pConstrParams,
  		Plan pEnclosing) {
  		
  		assert pConstructor != null;
  		assert pEnclosing != null;
  		assert pConstrParams != null;
  		
  		returnType = pConstructor.getDeclaringClass();
  		conCons = pConstructor;
  		constrParams = pConstrParams;
  		enclosedBy = pEnclosing;
	}



	/**
	 * @return type of instance created by this plan
	 */
	public Class<?> getReturnType() {
		assert returnType != null;
		return returnType;
	}
	
  
	/**
	 * How to reproduce this value=object?
	 * - Variable or (recursive) constructor-chain for user-output after some
	 *   mehtod-call crashed using this object as param.
	 * - Example: new A(new B(1), null)
	*/
	public String toString(Class<?> testee) {  	
		StringBuffer res = new StringBuffer();
		String className = null;
		

		className = CodeGenFct.getName(conCons.getDeclaringClass(), testee);	//fully qualified class-name: Enc$Nested
		
		/* Top-level class constructor */
		if (conCons.getDeclaringClass().getDeclaringClass() == null) {				
			res.append("new " +className +"(");
		}

		/* Nested class constructor */
		else {
			/* Remove "Enc$" from "Enc$Nested" in class "Enc.Nested" */
//			String enclosingName = conCons.getDeclaringClass().getDeclaringClass().getName();	//Enc
//			String consName = className.substring(enclosingName.length()+1, className.length());//Nested			
//			className = enclosingName +"." +consName;	//Enc.Nested				
			
			/* Non-inner nested class */
			if (Modifier.isStatic(conCons.getDeclaringClass().getModifiers()) == true) {					
				res.append("new " +className +"(");	
			}
			
			/* Inner class needs enclosing instance
			 * e.g.: StaticFactory.produceEnclosingInstance().new I() */							
			else {										
				assert enclosedBy != null;					
				res.append(enclosedBy.toString() +".");
				res.append("new " +className.substring(className.indexOf('.')+1) +"(");
			}
		}
	
		/* Parameter tail: recurse */
		for (int i=0; i<constrParams.length; i++) {
   			if (i>0) { res.append(", "); }        			//separator		
			res.append(constrParams[i].toString(testee)); //value
		}		
		
		/* return assembeled String */
		res.append(")");	
		return res.toString();
	}  
}
