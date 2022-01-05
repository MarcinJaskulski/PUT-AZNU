package org.bp.gate.travel;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class OrdersIdentifierService {
	HashMap<String, OrderIds> ordersIds =  new HashMap<>();

	public String generateOrderId() {
		String orderId = UUID.randomUUID().toString();
		ordersIds.put(orderId, new OrderIds());
		return orderId;
	}

	public void assignChairsOrderId(String id, String orderId) {
		ordersIds.get(id).setChairsOrderId(orderId);
	}

	public void assignTableId(String id, String tableId) {
		ordersIds.get(id).setTableOrderId(tableId);
	}

	public String getChairsOrderId(String id) {
		return ordersIds.get(id).getChairsOrderId();
	}

	public String getTableOrderId(String id) {
		return ordersIds.get(id).getTableOrderId();
	}

	public static class OrderIds{
		private String chairsOrderId;
		private String tableOrderId;

		public String getChairsOrderId() {
			return chairsOrderId;
		}
		public void setChairsOrderId(String chairsOrderId) {
			this.chairsOrderId = chairsOrderId;
		}
		public String getTableOrderId() {
			return tableOrderId;
		}
		public void setTableOrderId(String tableOrderId) {
			this.tableOrderId = tableOrderId;
		}
	}
}