
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.ccd.services;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.FaultResponse;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 24, 2016
 */
public interface FCResponseService {

	/**
	 * Method to publish the fault response to kafka (for down stream processing)
	 * @param faultResponse
	 * @return - SUCCESS if the publish succeeded else FAILURE
	 */
	public StatusMessageDTO publishResponse(FaultResponse faultResponse);
	
	/**
	 * Method to persist the fault response to datastore
	 * @param faultResponse
	 * @return - SUCCESS if the save succeeded else FAILURE
	 */
	public StatusMessageDTO persistResponse(FaultResponse faultResponse);
	
	/**
	 * Method to get the response of the fault event send from CCD
	 * @param faultRowIdentifier
	 * @return
	 */
	public FaultResponse getFaultEventResponse(String faultRowIdentifier);
}
