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
package org.apache.commons.collections.primitives.decorators;

import org.apache.commons.collections.primitives.IntIterator;
import org.apache.commons.collections.primitives.IntListIterator;

/**
 * 
 * @since Commons Primitives 1.0
 * @version $Revision: 1.4 $ $Date: 2004/02/25 20:46:27 $
 * 
 * @author Rodney Waldhoff 
 */
abstract class ProxyIntListIterator extends ProxyIntIterator implements IntListIterator {
    ProxyIntListIterator() {
    }
    
    public boolean hasPrevious() {
        return getListIterator().hasPrevious();
    }

    public int nextIndex() {
        return getListIterator().nextIndex();
    }

    public int previous() {
        return getListIterator().previous();
    }

    public int previousIndex() {
        return getListIterator().previousIndex();
    }

    protected final IntIterator getIterator() {
        return getListIterator();    
    }

    protected abstract IntListIterator getListIterator();

}
