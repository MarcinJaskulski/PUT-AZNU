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

	public void assignChairsOrderId(String id, int trainBookingId) {
		ordersIds.get(id).setChairsOrderId(trainBookingId);
	}

	public void assignTableId(String id, int carBookingId) {
		ordersIds.get(id).setTableOrderId(carBookingId);
	}

	public int getChairsOrderId(String id) {
		return ordersIds.get(id).getChairsOrderId();
	}

	public int getTableOrderId(String id) {
		return ordersIds.get(id).getTableOrderId();
	}

	public static class OrderIds{
		private int chairsOrderId;
		private int tableOrderId;

		public int getChairsOrderId() {
			return chairsOrderId;
		}
		public void setChairsOrderId(int chairsOrderId) {
			this.chairsOrderId = chairsOrderId;
		}
		public int getTableOrderId() {
			return tableOrderId;
		}
		public void setTableOrderId(int tableOrderId) {
			this.tableOrderId = tableOrderId;
		}
	}
}