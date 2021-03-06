/*
*  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.hierarchical.tenant.mgt.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.hierarchical.tenant.mgt.Configuration;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationHandlerChain;
import org.wso2.carbon.hierarchical.tenant.mgt.authorization.AuthorizationStatus;
import org.wso2.carbon.hierarchical.tenant.mgt.bean.PermissionGroup;
import org.wso2.carbon.hierarchical.tenant.mgt.permissions.TenantPermissionStore;
import org.wso2.carbon.hierarchical.tenant.mgt.Constants;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/permissions")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class PermissionGroupResource extends AbstractResource {
    private final static Log log = LogFactory.getLog(PermissionGroupResource.class);
    private final TenantPermissionStore store = Configuration.TENANT_PERMISSION_STORE;

    @POST
    public Response addPermissionGroup(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            PermissionGroup permissionGroup) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            store.addPermissionGroup(permissionGroup);
            return handleResponse(ResponseStatus.SUCCESS, "successfully added permission group : " +
                    permissionGroup.getId());
        } catch (Exception e) {
            String msg = "Error while adding permissionGroup";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @GET
    @Path("{group-name}")
    public Response getPermissionGroup(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            @PathParam("group-name") String groupName) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            PermissionGroup permissionGroup = store.getPermissionGroup(groupName);
            return Response.ok().entity(permissionGroup).build();
        } catch (Exception e) {
            String msg = "Error while retrieving permissionGroup";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }

    @DELETE
    @Path("{group-name}")
    public Response deletePermissionGroup(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            @PathParam("group-name") String groupName) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            store.deletePermissionGroup(groupName);
            return handleResponse(ResponseStatus.SUCCESS, "successfully deleted permission " +
                    "group : " + groupName);
        } catch (Exception e) {
            String msg = "Error while deleting permissionGroup";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }
    
    @PUT
    public Response updatePermissionGroup(
            @HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
            PermissionGroup permissionGroup) {
        try {
            AuthorizationHandlerChain authChain = new AuthorizationHandlerChain();
            authChain.addUserAuthHandler(authorization, Constants.MODIFY_TENANT_PERMISSION);
            AuthorizationStatus status = authChain.getAuthorizationStatus();
            if (!status.isAuthorized()) {
                return handleResponse(ResponseStatus.FORBIDDEN, status.getMessage());
            }
            store.deletePermissionGroup(permissionGroup.getId());
            store.addPermissionGroup(permissionGroup);
            return handleResponse(ResponseStatus.SUCCESS, "successfully updated permission group : " +
                    permissionGroup.getId());
        } catch (Exception e) {
            String msg = "Error while updating permissionGroup";
            log.error(msg, e);
            return handleResponse(ResponseStatus.FAILED, msg);
        }
    }
}
