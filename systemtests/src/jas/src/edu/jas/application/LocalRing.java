/*
 * $Id: LocalRing.java 1663 2008-02-05 17:32:07Z kredel $
 */

package edu.jas.application;

import java.util.Random;
import java.io.Reader;

import org.apache.log4j.Logger;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;


/**
 * Local ring class based on GenPolynomial with RingElem interface.
 * Objects of this class are effective immutable.
 * @author Heinz Kredel
 */
public class LocalRing<C extends GcdRingElem<C> > 
             implements RingFactory< Local<C> >  {

     private static final Logger logger = Logger.getLogger(LocalRing.class);
     //private boolean debug = logger.isDebugEnabled();


    /** Polynomial ideal for localization. 
     */
    protected final Ideal<C> ideal;


    /** Polynomial ring of the factory. 
     */
    protected final GenPolynomialRing<C> ring;


    /** Indicator if this ring is a field.
     */
    protected int isField = -1; // initially unknown


    /** The constructor creates a LocalRing object 
     * from a GenPolynomialRing and a GenPolynomial. 
     * @param i localization polynomial ideal.
     */
    public LocalRing(Ideal<C> i) {
        if ( i == null ) {
           throw new RuntimeException("ideal may not be null");
        }
        ideal = i.GB(); // cheap if isGB
        if ( ideal.isONE() ) {
           throw new RuntimeException("ideal may not be 1");
        }
        ring = ideal.list.ring;
    }


    /** Copy Local element c.
     * @param c
     * @return a copy of c.
     */
    public Local<C> copy(Local<C> c) {
        return new Local<C>( c.ring, c.num, c.den, true );
    }


    /** Get the zero element.
     * @return 0 as Local.
     */
    public Local<C> getZERO() {
        return new Local<C>( this, ring.getZERO() );
    }


    /**  Get the one element.
     * @return 1 as Local.
     */
    public Local<C> getONE() {
        return new Local<C>( this, ring.getONE() );
    }

    
    /**
     * Query if this ring is commutative.
     * @return true if this ring is commutative, else false.
     */
    public boolean isCommutative() {
        return ring.isCommutative();
    }


    /**
     * Query if this ring is associative.
     * @return true if this ring is associative, else false.
     */
    public boolean isAssociative() {
        return ring.isAssociative();
    }


    /**
     * Query if this ring is a field.
     * @return false.
     */
    public boolean isField() {
        if ( isField > 0 ) { 
           return true;
        }
        if ( isField == 0 ) { 
           return false;
        }
        // ??
        return false;
    }


    /**
     * Characteristic of this ring.
     * @return characteristic of this ring.
     */
    public java.math.BigInteger characteristic() {
        return ring.characteristic();
    }


    /** Get a Local element from a BigInteger value.
     * @param a BigInteger.
     * @return a Local.
     */
    public Local<C> fromInteger(java.math.BigInteger a) {
        return new Local<C>( this, ring.fromInteger(a) );
    }


    /** Get a Local element from a long value.
     * @param a long.
     * @return a Local.
     */
    public Local<C> fromInteger(long a) {
        return new Local<C>( this, ring.fromInteger(a) );
    }
    

    /** Get the String representation as RingFactory.
     * @see java.lang.Object#toString()
     */
    @Override
     public String toString() {
        return "Local[ " 
                + ideal.toString() + " ]";
    }


    /** Comparison with any other object.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked") // not jet working
    public boolean equals(Object b) {
        if ( ! ( b instanceof LocalRing ) ) {
           return false;
        }
        LocalRing<C> a = null;
        try {
            a = (LocalRing<C>) b;
        } catch (ClassCastException e) {
        }
        if ( a == null ) {
            return false;
        }
        if ( ! ring.equals( a.ring ) ) {
            return false;
        }
        return ideal.equals( a.ideal );
    }


    /** Hash code for this local ring.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() { 
       int h;
       h = ideal.hashCode();
       return h;
    }


    /** Local random.
     * @param n such that 0 &le; v &le; (2<sup>n</sup>-1).
     * @return a random residue element.
     */
    public Local<C> random(int n) {
      GenPolynomial<C> r = ring.random( n ).monic();
      GenPolynomial<C> s = ring.random( n ).monic();
      s = ideal.normalform( s );
      while ( s.isZERO() ) {
          logger.info("s was in ideal");
          s = ring.random( n ).monic();
          s = ideal.normalform( s );
      }
      return new Local<C>( this, r, s, false );
    }


    /**
     * Generate a random residum polynomial.
     * @param k bitsize of random coefficients.
     * @param l number of terms.
     * @param d maximal degree in each variable.
     * @param q density of nozero exponents.
     * @return a random residue polynomial.
     */
    public Local<C> random(int k, int l, int d, float q) {
      GenPolynomial<C> r = ring.random(k,l,d,q).monic();
      GenPolynomial<C> s = ring.random(k,l,d,q).monic();
      s = ideal.normalform( s );
      while ( s.isZERO() ) {
          logger.info("s was in ideal "+ideal);
          s = ring.random( k,l,d,q ).monic();
          s = ideal.normalform( s );
      }
      return new Local<C>( this, r, s, false );
    }


    /** Local random.
     * @param n such that 0 &le; v &le; (2<sup>n</sup>-1).
     * @param rnd is a source for random bits.
     * @return a random residue element.
     */
    public Local<C> random(int n, Random rnd) {
      GenPolynomial<C> r = ring.random( n, rnd ).monic();
      GenPolynomial<C> s = ring.random( n, rnd ).monic();
      s = ideal.normalform( s );
      while ( s.isZERO() ) {
          logger.info("s was in ideal");
          s = ring.random( n, rnd ).monic();
          s = ideal.normalform( s );
      }
      return new Local<C>( this, r, s, false);
    }


    /** Parse Local from String.
     * @param s String.
     * @return Local from s.
     */
    public Local<C> parse(String s) {
        GenPolynomial<C> x = ring.parse( s );
        return new Local<C>( this, x );
    }


    /** Parse Local from Reader.
     * @param r Reader.
     * @return next Local from r.
     */
    public Local<C> parse(Reader r) {
	throw new RuntimeException("not supported.");
    }

}
