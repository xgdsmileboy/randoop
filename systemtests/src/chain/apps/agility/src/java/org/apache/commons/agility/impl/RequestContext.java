/*
 * $Header: /home/cvspublic/jakarta-commons/chain/apps/agility/src/java/org/apache/commons/agility/impl/RequestContext.java,v 1.1 2004/06/01 00:55:50 husted Exp $
 * $Revision: 1.1 $
 * $Date: 2004/06/01 00:55:50 $
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.agility.impl;

import org.apache.commons.agility.Request;
import org.apache.commons.agility.Response;
import org.apache.commons.chain.impl.ContextBase;

/**
 * Concrete implementation of Request based on a Commons Chain Context.
 */
public class RequestContext extends ContextBase implements Request {

    /**
     * Name field.
     */
    private String name;

    /**
     * Response field.
     */
    private Response response;

    /**
     * Constructor to create Request and set name.
     * @param name
     */
    public RequestContext(String name) {
        super();
        this.name = name;
    }

    // See interface for Javadoc
    public String getName() {
        return name;
    }

    // See interface for Javadoc
    public Response getResponse() {
        return response;
    }

    // See interface for Javadoc
    public void setResponse(Response response) {
        this.response = response;
    }
}
