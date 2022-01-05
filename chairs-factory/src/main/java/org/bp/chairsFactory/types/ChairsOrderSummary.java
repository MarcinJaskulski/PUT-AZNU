package org.bp.chairsFactory.types;

public class ChairsOrderSummary extends ChairsOrder {
	private String id;
	private int cost;


	public ChairsOrderSummary(){}

	public ChairsOrderSummary(ChairsOrder chairsOrder){
		this.setAmount(chairsOrder.getAmount());
		this.setMaterial(chairsOrder.getMaterial());
		this.setModel(chairsOrder.getModel());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
