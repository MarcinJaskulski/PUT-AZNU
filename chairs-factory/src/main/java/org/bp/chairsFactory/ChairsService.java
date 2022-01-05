package org.bp.chairsFactory;

import org.bp.chairsFactory.types.ChairsOrder;
import org.bp.chairsFactory.types.Fault;
import org.bp.chairsFactory.types.ChairsOrderSummary;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@javax.jws.WebService
@org.springframework.stereotype.Service
public class ChairsService {
	
	HashMap<String, ChairsOrderSummary> chairsOrders = new HashMap<>();

	public ChairsOrderSummary orderChairs(ChairsOrder chairsOrder) throws Fault{
		ChairsOrderSummary chairsOrderSummary = new ChairsOrderSummary(chairsOrder);
		chairsOrderSummary.setId(UUID.randomUUID().toString());

		if(chairsOrder.getMaterial().toLowerCase().equals("wood"))
			chairsOrderSummary.setCost(100 * chairsOrderSummary.getAmount());
		else
			chairsOrderSummary.setCost(70 * chairsOrderSummary.getAmount());

		if(chairsOrder.getModel().toLowerCase().equals("strange"))
			throw new Fault("Strange Chair Model");

		chairsOrders.put(chairsOrderSummary.getId(), chairsOrderSummary);

		return chairsOrderSummary;
	}

	public ChairsOrderSummary getChairsOrderSummary(String id) throws Fault{
		if(!chairsOrders.containsKey(id))
			throw new Fault("Order " + id + " does not exists");
		return chairsOrders.get(id);
	}
	
	public void cancelChairsOrder(String id) throws Fault{
		if(!chairsOrders.containsKey(id))
			throw new Fault("Order " + id + " does not exists");
		chairsOrders.remove(id);
	}
}

