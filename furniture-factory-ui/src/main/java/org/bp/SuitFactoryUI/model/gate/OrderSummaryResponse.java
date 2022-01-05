package org.bp.SuitFactoryUI.model.gate;

import org.bp.SuitFactoryUI.model.chairs.ChairsOrderSummary;
import org.bp.SuitFactoryUI.model.table.TableOrderSummary;

public class OrderSummaryResponse {
    private String id;
    private ChairsOrderSummary chairsOrderSummary;
    private TableOrderSummary tableOrderSummary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChairsOrderSummary getChairsOrderSummary() {
        return chairsOrderSummary;
    }

    public void setChairsOrderSummary(ChairsOrderSummary chairsOrderSummary) {
        this.chairsOrderSummary = chairsOrderSummary;
    }

    public TableOrderSummary getTableOrderSummary() {
        return tableOrderSummary;
    }

    public void setTableOrderSummary(TableOrderSummary tableOrderSummary) {
        this.tableOrderSummary = tableOrderSummary;
    }
}
