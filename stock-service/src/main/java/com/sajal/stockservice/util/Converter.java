package com.sajal.stockservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class Converter {
	private final ObjectMapper mapper;
	public String toJSON(final Object object){
		try {
			return mapper.writeValueAsString(object);
		}catch (JsonProcessingException e){
			throw new IllegalArgumentException("cannot convert "+ object+ " to json",e);
		}
	}
	public <T> T toObject(String json, Class<T> tClass){
		try {
			return mapper
					.readValue(json,tClass);
		}catch (IOException e){
			throw new IllegalArgumentException("Cannot convert " + json + " to object type " + tClass.getSimpleName(), e);
		}
	}
}
