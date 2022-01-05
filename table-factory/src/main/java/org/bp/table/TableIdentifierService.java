package org.bp.table;


import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TableIdentifierService {
	
	public String getId() {
		return UUID.randomUUID().toString();
	}

}
