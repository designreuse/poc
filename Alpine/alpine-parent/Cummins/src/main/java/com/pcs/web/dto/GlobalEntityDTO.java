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
package com.pcs.web.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * GlobalEntityDTO
 *
 * @description DTO for Global Entity
 * @author Twinkle (pcseg297)
 * @date August 2014
 * @since galaxy-1.0.0
 */
public class GlobalEntityDTO implements Serializable {
	private static final long serialVersionUID = 4258951619080193018L;
	private UUID uuid;
	private String description;
	private String globalEntityType;
	private String status;
	private String typeCode;
	private Boolean isDefault;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGlobalEntityType() {
	    return globalEntityType;
    }

	public void setGlobalEntityType(String globalEntityType) {
	    this.globalEntityType = globalEntityType;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Boolean getIsDefault() {
	    return isDefault;
    }

	public void setIsDefault(Boolean isDefault) {
	    this.isDefault = isDefault;
    }

}