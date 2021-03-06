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
package com.pcs.device.activity.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.device.activity.enums.Status;

/**
 * StatusMessageDTO
 * 
 * @description DTO which sends status message information to the UI.
 * @author Riyas PH (pcseg296)
 */
@XmlRootElement
public class StatusMessageDTO implements Serializable {

	private static final long serialVersionUID = -4548949833946345612L;
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
