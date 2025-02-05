/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.jaxrs.fat.subresource;

public class GuestbookException extends Exception {

    private static final long serialVersionUID = -1975560538784455458L;

    public GuestbookException() {
        super();
    }

    public GuestbookException(String message) {
        super(message);
    }

    public GuestbookException(Throwable cause) {
        super(cause);
    }

    public GuestbookException(String message, Throwable cause) {
        super(message, cause);
    }

}
