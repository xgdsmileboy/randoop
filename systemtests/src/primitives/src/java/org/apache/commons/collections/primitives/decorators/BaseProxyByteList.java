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

import org.apache.commons.collections.primitives.ByteCollection;
import org.apache.commons.collections.primitives.ByteList;
import org.apache.commons.collections.primitives.ByteListIterator;

/**
 * 
 * @since Commons Primitives 1.0
 * @version $Revision: 1.2 $ $Date: 2004/02/25 20:46:27 $
 * 
 * @author Rodney Waldhoff 
 */
abstract class BaseProxyByteList extends BaseProxyByteCollection implements ByteList {
    protected abstract ByteList getProxiedList();

    protected final ByteCollection getProxiedCollection() {
        return getProxiedList();
    }

    protected BaseProxyByteList() {
    }

    public void add(int index, byte element) {
        getProxiedList().add(index,element);
    }

    public boolean addAll(int index, ByteCollection collection) {        
        return getProxiedList().addAll(index,collection);
    }

    public byte get(int index) {
        return getProxiedList().get(index);
    }

    public int indexOf(byte element) {
        return getProxiedList().indexOf(element);
    }

    public int lastIndexOf(byte element) {
        return getProxiedList().lastIndexOf(element);
    }

    public ByteListIterator listIterator() {
        return getProxiedList().listIterator();
    }

    public ByteListIterator listIterator(int index) {
        return getProxiedList().listIterator(index);
    }

    public byte removeElementAt(int index) {
        return getProxiedList().removeElementAt(index);
    }

    public byte set(int index, byte element) {
        return getProxiedList().set(index,element);
    }

    public ByteList subList(int fromIndex, int toIndex) {
        return getProxiedList().subList(fromIndex,toIndex);
    }

}
