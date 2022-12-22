package com.ccclogic.nerve.config.security;


import com.ccclogic.nerve.util.constants.Constants;
import org.apache.commons.lang3.StringUtils;

public class TenantContext {

	private static ThreadLocal<String> ccSchema = new ThreadLocal<>();
	private static ThreadLocal<Long> ccId = new ThreadLocal<>();
	
	public static String getCurrentCCSchema(){
		String schema =  ccSchema.get();
		
		return StringUtils.isNotBlank(schema)? schema : Constants.DATASOURCE.DEFAULT_SCHEMA;
	}
	
	public static void setCurrentCCSchema(String schema){
		ccSchema.set(schema);
	}

	public static Long getCurrentCcId() {
		return ccId.get();
	}

	public static void setCurrentCcId(Long ccid) {
		ccId.set(ccid);
	}
}
