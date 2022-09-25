package com.ccclogic.fusion.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String convertObjectToJsonString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String convertObjectWithViewToJsonString(Object obj, Class viewClass){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithView(viewClass).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static ArrayNode createJsonArrayObject(){
		ObjectMapper mapper = new ObjectMapper();
		return mapper.createArrayNode();
	}

	public static ObjectNode createJsonObject(){
		ObjectMapper mapper = new ObjectMapper();
		return mapper.createObjectNode();
	}

	public static ObjectNode getJsonObjectFromString(String jsonString) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = null;
		parser = factory.createParser(jsonString);
		return mapper.readTree(parser);
	}

	public static boolean isValid(String jsonString) throws IOException{
		ObjectMapper mapper = new ObjectMapper();

		JsonFactory factory = mapper.getFactory();
		JsonParser parser = null;
		try {
			parser = factory.createParser(jsonString);
			return true;
		}catch (JsonParseException e){
			return false;
		}catch (IOException e){
			logger.error("Error while parsing json");
			throw new IOException();
		}
	}

	public static List<ObjectNode> getObjectList(String jsonString) throws IOException {
		ObjectNode rootNode = getJsonObjectFromString(jsonString);
		List<ObjectNode> objects = new ArrayList<>();
		getObjectList(rootNode, objects);
		return objects;
	}

	private static void getObjectList(JsonNode rootNode, List<ObjectNode> objects){
		if(rootNode.isArray()){
			for(int i=0; i<rootNode.size(); i++){
				if(rootNode.get(i).isObject() || rootNode.get(i).isArray()){
					getObjectList((ObjectNode) rootNode.get(i), objects);
				}
			}
		}
		else if(rootNode.isObject()){
			objects.add((ObjectNode) rootNode);
			Iterator<String> fieldIterator = rootNode.fieldNames();
			while(fieldIterator.hasNext()) {
				String field = fieldIterator.next();
				if(rootNode.get(field).isObject() || rootNode.get(field).isArray()){
					getObjectList(rootNode.get(field), objects);
				}
			}
		}
	}
}
