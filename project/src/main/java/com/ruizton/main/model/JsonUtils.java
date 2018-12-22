package com.ruizton.main.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * json转换工具类
 */
public final class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {};
    
    public static String toJsonFormat(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
    
    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToObj(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * obj转jsonp
     * <p>Title: jsonToJsonp</p>
     * <p>Description: </p>
     * @param callback 回调接口名
     * @param obj 待转换对象
     * @return jsonp
     */
    public static String objToJsonp(String callback , Object obj){
    	if(StringUtils.isNotBlank(callback)){
    		
    		StringBuilder sb = new StringBuilder(callback);
    		sb.append("(");
    		sb.append(objectToJson(obj));
    		sb.append(");");
    		
//    		String jsonp = callback+"("+objectToJson(obj)+");";
    		return sb.toString();
    	}else{
    		return objectToJson(obj);
    	}
    }
    
    /**
	 * Json转Map对象
	 */
	public static <T> Map<String, T> toMap(String json, Class<T> clz) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, T>>() {
		}.getType();
		return gson.fromJson(json, type);
	}
}
