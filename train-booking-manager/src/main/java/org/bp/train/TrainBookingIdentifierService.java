package org.bp.train;


import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrainBookingIdentifierService {
	
	public String getMessageIdentifier() {
		return UUID.randomUUID().toString();
	}

}
