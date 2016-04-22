/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------------
 * TimePeriodValues.java
 * ---------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Apr-2003 : Version 1 (DG);
 * 30-Jul-2003 : Added clone and equals methods while testing (DG);
 * 11-Mar-2005 : Fixed bug in bounds recalculation - see bug report 
 *               1161329 (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 03-Oct-2006 : Fixed NullPointerException in equals(), fire change event in 
 *               add() method, updated API docs (DG);
 * 21-Jun-2007 : Removed JCommon dependencies (DG);
 * 29-Jun-2007 : Changed first parameter in constructors from String to 
 *               Comparable (DG);
 * 07-Apr-2008 : Fixed bug with maxMiddleIndex in updateBounds() (DG);
 *
 */

package mytest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart.Series;

/**
 * A structure containing zero, one or many {@link TimePeriodValue} instances.  
 * The time periods can overlap, and are maintained in the order that they are 
 * added to the collection.
 * <p>
 * This is similar to the {@link TimeSeries} class, except that the time 
 * periods can have irregular lengths.
 */
public class DummyClass2 implements Serializable {

    /** For serialization. */
    static final long serialVersionUID = -2210593619794989709L;
    
    /** Default value for the domain description. */
    protected static final String DEFAULT_DOMAIN_DESCRIPTION = "Time";

    /** Default value for the range description. */
    protected static final String DEFAULT_RANGE_DESCRIPTION = "Value";

    /** A description of the domain. */
    private String domain;

    /** A description of the range. */
    private String range;

    /** The list of data pairs in the series. */
    private List data;

    /** Index of the time period with the minimum start milliseconds. */
    private int minStartIndex = -1;
    
    /** Index of the time period with the maximum start milliseconds. */
    private int maxStartIndex = -1;
    
    /** Index of the time period with the minimum middle milliseconds. */
    private int minMiddleIndex = -1;
    
    /** Index of the time period with the maximum middle milliseconds. */
    private int maxMiddleIndex = -1;
    
    /** Index of the time period with the minimum end milliseconds. */
    private int minEndIndex = -1;
    
    /** Index of the time period with the maximum end milliseconds. */
    private int maxEndIndex = -1;

    /**
     * Creates a new (empty) collection of time period values.
     *
     * @param name  the name of the series (<code>null</code> not permitted).
     */
    public DummyClass2(Comparable name) {
        this(name, DEFAULT_DOMAIN_DESCRIPTION, DEFAULT_RANGE_DESCRIPTION);
    }

    /**
     * Creates a new time series that contains no data.
     * <P>
     * Descriptions can be specified for the domain and range.  One situation
     * where this is helpful is when generating a chart for the time series -
     * axis labels can be taken from the domain and range description.
     *
     * @param name  the name of the series (<code>null</code> not permitted).
     * @param domain  the domain description.
     * @param range  the range description.
     */
    public DummyClass2(Comparable name, String domain, String range) {
        this.domain = domain;
        this.range = range;
        this.data = new ArrayList();
    }

    /**
     * Returns the domain description.
     *
     * @return The domain description (possibly <code>null</code>).
     * 
     * @see #getRangeDescription()
     * @see #setDomainDescription(String)
     */
    public String getDomainDescription() {
        return this.domain;
    }

    /**
     * Sets the domain description and fires a property change event (with the
     * property name <code>Domain</code> if the description changes).
     *
     * @param description  the new description (<code>null</code> permitted).
     * 
     * @see #getDomainDescription()
     */
    public void setDomainDescription(String description) {
        String old = this.domain;
        this.domain = description;
    }

    /**
     * Returns the range description.
     *
     * @return The range description (possibly <code>null</code>).
     * 
     * @see #getDomainDescription()
     * @see #setRangeDescription(String)
     */
    public String getRangeDescription() {
        return this.range;
    }

    /**
     * Sets the range description and fires a property change event with the
     * name <code>Range</code>.
     *
     * @param description  the new description (<code>null</code> permitted).
     * 
     * @see #getRangeDescription()
     */
    public void setRangeDescription(String description) {
        String old = this.range;
        this.range = description;
    }

    /**
     * Returns the number of items in the series.
     *
     * @return The item count.
     */
    public int getItemCount() {
        return this.data.size();
    }


    /**
     * Adds a data item to the series and sends a {@link SeriesChangeEvent} to
     * all registered listeners.
     *
     * @param item  the item (<code>null</code> not permitted).
     */
    public void add(Object item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed.");
        }
        this.data.add(item);

    }
    
    /**
     * Update the index values for the maximum and minimum bounds.
     * 
     * @param period  the time period.
     * @param index  the index of the time period.
     */
    private void updateBounds(Object period, int index) {
        
        long start = 0;
        long end = 0;
        long middle = start + ((end - start) / 2);

        if (this.minStartIndex >= 0) {
            long minStart = 0;
            if (start < minStart) {
                this.minStartIndex = index;           
            }
        }
        else {
            this.minStartIndex = index;
        }
        
        if (this.maxStartIndex >= 0) {
            long maxStart = 0;
            if (start > maxStart) {
                this.maxStartIndex = index;           
            }
        }
        else {
            this.maxStartIndex = index;
        }
        
        if (this.minMiddleIndex >= 0) {
            long s = 0;
            long e = 0;
            long minMiddle = s + (e - s) / 2;
            if (middle < minMiddle) {
                this.minMiddleIndex = index;           
            }
        }
        else {
            this.minMiddleIndex = index;
        }
        
        if (this.maxMiddleIndex >= 0) {
            long s = 0;
            long e = 0;
            long maxMiddle = s + (e - s) / 2;
            if (middle > maxMiddle) {
                this.maxMiddleIndex = index;           
            }
        }
        else {
            this.maxMiddleIndex = index;
        }
        
        if (this.minEndIndex >= 0) {
            long minEnd = 0;
            if (end < minEnd) {
                this.minEndIndex = index;           
            }
        }
        else {
            this.minEndIndex = index;
        }
       
        if (this.maxEndIndex >= 0) {
            long maxEnd = 0;
            if (end > maxEnd) {
                this.maxEndIndex = index;           
            }
        }
        else {
            this.maxEndIndex = index;
        }
        
    }
    
    /**
     * Recalculates the bounds for the collection of items.
     */
    private void recalculateBounds() {
        this.minStartIndex = -1;
        this.minMiddleIndex = -1;
        this.minEndIndex = -1;
        this.maxStartIndex = -1;
        this.maxMiddleIndex = -1;
        this.maxEndIndex = -1;
        for (int i = 0; i < this.data.size(); i++) {
        }
    }


    /**
     * Deletes data from start until end index (end inclusive) and sends a
     * {@link SeriesChangeEvent} to all registered listeners.
     *
     * @param start  the index of the first period to delete.
     * @param end  the index of the last period to delete.
     */
    public void delete(int start, int end) {
        for (int i = 0; i <= (end - start); i++) {
            this.data.remove(start);
        }
        recalculateBounds();
    }
    
    /**
     * Tests the series for equality with another object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> or <code>false</code>.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DummyClass2)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        DummyClass2 that = (DummyClass2) obj;
     
        int count = getItemCount();
        if (count != that.getItemCount()) {
            return false;
        }
        for (int i = 0; i < count; i++) {
           
        }
        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    public int hashCode() {
        int result;
        result = (this.domain != null ? this.domain.hashCode() : 0);
        result = 29 * result + (this.range != null ? this.range.hashCode() : 0);
        result = 29 * result + this.data.hashCode();
        result = 29 * result + this.minStartIndex;
        result = 29 * result + this.maxStartIndex;
        result = 29 * result + this.minMiddleIndex;
        result = 29 * result + this.maxMiddleIndex;
        result = 29 * result + this.minEndIndex;
        result = 29 * result + this.maxEndIndex;
        return result;
    }


    
    /**
     * Returns the index of the time period with the minimum start milliseconds.
     * 
     * @return The index.
     */
    public int getMinStartIndex() {
        return this.minStartIndex;
    }
    
    /**
     * Returns the index of the time period with the maximum start milliseconds.
     * 
     * @return The index.
     */
    public int getMaxStartIndex() {
        return this.maxStartIndex;
    }

    /**
     * Returns the index of the time period with the minimum middle 
     * milliseconds.
     * 
     * @return The index.
     */
    public int getMinMiddleIndex() {
        return this.minMiddleIndex;
    }
    
    /**
     * Returns the index of the time period with the maximum middle 
     * milliseconds.
     * 
     * @return The index.
     */
    public int getMaxMiddleIndex() {
        return this.maxMiddleIndex;
    }

    /**
     * Returns the index of the time period with the minimum end milliseconds.
     * 
     * @return The index.
     */
    public int getMinEndIndex() {
        return this.minEndIndex;
    }
    
    /**
     * Returns the index of the time period with the maximum end milliseconds.
     * 
     * @return The index.
     */
    public int getMaxEndIndex() {
        return this.maxEndIndex;
    }

}
