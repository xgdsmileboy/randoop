/*
 * Copyright 2003-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections.primitives;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections.iterators.AbstractTestIterator;
import org.apache.commons.collections.primitives.adapters.IntIteratorIterator;

/**
 * @version $Revision: 1.3 $ $Date: 2004/02/25 20:46:30 $
 * @author Rodney Waldhoff
 */
public abstract class TestIntIterator extends AbstractTestIterator {

    // conventional
    // ------------------------------------------------------------------------

    public TestIntIterator(String testName) {
        super(testName);
    }

    // collections testing framework
    // ------------------------------------------------------------------------

    public Iterator makeEmptyIterator() {
        return IntIteratorIterator.wrap(makeEmptyIntIterator());
    }

    public Iterator makeFullIterator() {
        return IntIteratorIterator.wrap(makeFullIntIterator());
    }


    protected abstract IntIterator makeEmptyIntIterator();
    protected abstract IntIterator makeFullIntIterator();
    protected abstract int[] getFullElements();

    // tests
    // ------------------------------------------------------------------------
    
    public void testNextHasNextRemove() {
        int[] elements = getFullElements();
        IntIterator iter = makeFullIntIterator();
        for(int i=0;i<elements.length;i++) {
            assertTrue(iter.hasNext());
            assertEquals(elements[i],iter.next());
            if(supportsRemove()) {
                iter.remove();
            }
        }        
        assertTrue(! iter.hasNext() );
    }

    public void testEmptyIntIterator() {
        assertTrue( ! makeEmptyIntIterator().hasNext() );
        try {
            makeEmptyIntIterator().next();
            fail("Expected NoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
        if(supportsRemove()) {
            try {
                makeEmptyIntIterator().remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }

    public void testRemoveBeforeNext() {
        if(supportsRemove()) {
            try {
                makeFullIntIterator().remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }

    public void testRemoveAfterRemove() {
        if(supportsRemove()) {
            IntIterator iter = makeFullIntIterator();
            iter.next();
            iter.remove();
            try {
                iter.remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }
}
