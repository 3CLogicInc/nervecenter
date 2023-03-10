package com.ccclogic.nerve.util;

import com.ccclogic.nerve.config.security.TenantContext;

public class CallCenterUtil {
	
	public static String generateCallCenterSchemaNameById(Long ccid){
		return "tenant_"+ccid;
	}
	
	public static void setCCSchemaInContext(String ccId){
		String schema = generateCallCenterSchemaNameById(Long.parseLong(ccId));
		TenantContext.setCurrentCCSchema(schema);
	}

	public static void setCCIdInContext(String ccId){
		TenantContext.setCurrentCcId(Long.parseLong(ccId));
	}

	public static String getClientIdFromCallcenterName(String ccName) {
		return ccName.trim().replace(" ", "").toLowerCase();
	}

	public static String getUsername(String username){
		return username.split("@")[0];
	}

	public static String getCustomerName(String username){
		return username.split("@")[1];
	}

}
