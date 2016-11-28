package com.xs.dzyxh.manager.job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	public static 	ObjectMapper mapper = new ObjectMapper(); 
	static{
  		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;   
		mapper .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
