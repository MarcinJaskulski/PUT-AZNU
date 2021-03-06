package org.bp.SuitFactoryUI.model.gate;

import org.bp.SuitFactoryUI.model.chairs.ChairsOrder;
import org.bp.SuitFactoryUI.model.table.TableOrder;

public class OrderRequest {
    private ChairsOrder chairsOrder;
    private TableOrder tableOrder;

    public ChairsOrder getChairsOrder() {
        return chairsOrder;
    }

    public void setChairsOrder(ChairsOrder chairsOrder) {
        this.chairsOrder = chairsOrder;
    }

    public TableOrder getTableOrder() {
        return tableOrder;
    }

    public void setTableOrder(TableOrder tableOrder) {
        this.tableOrder = tableOrder;
    }
}
