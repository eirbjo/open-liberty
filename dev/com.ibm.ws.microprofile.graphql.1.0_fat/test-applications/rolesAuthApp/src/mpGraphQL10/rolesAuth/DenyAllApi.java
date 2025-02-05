/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
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
package mpGraphQL10.rolesAuth;

import static mpGraphQL10.rolesAuth.RolesAuthTestServlet.ACCESSED;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@DenyAll
@GraphQLApi
public class DenyAllApi {

    @PermitAll
    @Query("denyAll_permitAll")
    public String permitAll() { return ACCESSED; }
    
    @Query("denyAll_unannotated")
    public String unannotated() { return ACCESSED; }
    
    @DenyAll
    @Query("denyAll_denyAll")
    public String denyAll() { return ACCESSED; }
    
    @RolesAllowed("Role1")
    @Query("denyAll_rolesAllowed1")
    public String rolesAllowed1() { return ACCESSED; }
    
    @RolesAllowed("Role2")
    @Query("denyAll_rolesAllowed2")
    public String rolesAllowed2() { return ACCESSED; }
}
