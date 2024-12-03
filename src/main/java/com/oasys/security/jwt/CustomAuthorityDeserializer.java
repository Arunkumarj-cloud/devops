package com.oasys.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorityDeserializer extends JsonDeserializer<Collection<? extends GrantedAuthority>>{

	
	
	@Override
	public Collection<? extends GrantedAuthority> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);
		List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

		Iterator<JsonNode> elements = jsonNode.elements();
		while (elements.hasNext()) {
			JsonNode next = elements.next();
			JsonNode authority = next.get("authority");
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
		}
		return grantedAuthorities;
	}
	
}
