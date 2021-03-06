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
package com.pcs.avocado.dto;

import java.util.List;

import com.pcs.avocado.commons.dto.IdentityDTO;

/**
 * Device Bulk Assign DTO
 * 
 * @author pcseg296 (RIYAS PH)
 * @date MAY 2016
 * @since Avocado-1.0.0
 */
public class OwnerDeviceDTO {

	private List<ClientDeviceDTO> clientDevices;
	private IdentityDTO owner;

	public List<ClientDeviceDTO> getClientDevices() {
		return clientDevices;
	}

	public void setClientDevices(List<ClientDeviceDTO> clientDevices) {
		this.clientDevices = clientDevices;
	}

	public IdentityDTO getOwner() {
		return owner;
	}

	public void setOwner(IdentityDTO owner) {
		this.owner = owner;
	}

}
