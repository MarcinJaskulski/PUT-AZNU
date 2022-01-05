package org.bp.table.model;

public class TableOrderSummary extends TableOrder {
  private String id;
  private int cost;

  public TableOrderSummary(){}


  public TableOrderSummary(TableOrder table){
    this.model = table.model;
    this.material = table.material;
    this.size = table.size;
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
