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

package com.pcs.avocado.enums;

/**
 * AlpineCoreStatus
 * 
 * @description status and the status codes
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */

public enum AlpineISStatus {

	FALSE("false"), TRUE("true");

	private String statusCode;

	private AlpineISStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getstatusCode() {
		return statusCode;
	}
}
