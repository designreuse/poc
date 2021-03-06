/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.galaxy.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.galaxy.autoclaim.RegisterDevice;
import com.pcs.galaxy.dto.Device;

/**
 * RegisterDeviceResource
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Path("/device")
@Component
public final class RegisterDeviceResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(RegisterDeviceResource.class);

	@Autowired
	private RegisterDevice registerDevice;

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response autoClaimDevice(Device device) {
		LOGGER.info("***************AUTO_CLAIM***************");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(registerDevice.onBoardDevice(device));
		return responseBuilder.build();
	}

}
