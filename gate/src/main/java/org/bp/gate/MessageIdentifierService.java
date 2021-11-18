package org.bp.gate;


import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageIdentifierService {
	
	public String getMessageIdentifier() {
		return UUID.randomUUID().toString();
	}

}
