package org.bp.table;

import org.bp.table.model.TableException;
import org.bp.table.model.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.UUID;


@org.springframework.web.bind.annotation.RestController


@OpenAPIDefinition(info = @Info(
		title = "Table factory service",
		version = "1",
		description = "Service for booking train ticket"))

public class TableService {
	HashMap<String, TableOrderSummary> tableOrders = new HashMap<>();

	@org.springframework.web.bind.annotation.PostMapping("/tableOrder")
	@Operation(
			summary = "order table operation",
			description = "operation for order table",
			responses = {
					@ApiResponse(responseCode = "200",
							description = "OK",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TableOrderSummary.class))}),
					@ApiResponse(responseCode = "400", description = "Bad Request",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TableException.class))})
			})
	public TableOrderSummary orderTable(
			@org.springframework.web.bind.annotation.RequestBody TableOrder table
	) {
		TableOrderSummary tableOrderSummary = new TableOrderSummary(table);
		tableOrderSummary.setId(UUID.randomUUID().toString());
		tableOrderSummary.setCost(2000);

		if(table.getModel().toLowerCase() == "strange")
			throw new TableException("Strange Table Model");

		tableOrders.put(tableOrderSummary.getId(), tableOrderSummary);
		return tableOrderSummary;
    }

	@Operation(
			summary = "Get table order",
			description = "Get table order",
			responses = {
					@ApiResponse(responseCode = "200",
							description = "OK",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TableOrderSummary.class))}),
					@ApiResponse(responseCode = "400", description = "Bad Request",
							content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TableException.class))})
			})
	@org.springframework.web.bind.annotation.GetMapping("/tableOrder/{id}")
	public TableOrderSummary getTableOrder(@PathVariable String id) throws TableException{
		if(!tableOrders.containsKey(id))
			throw new TableException("Order " + id + " does not exists");
		return tableOrders.get(id);
	}

	@org.springframework.web.bind.annotation.DeleteMapping("/tableOrder/{id}")
	public void cancelTableOrder(@PathVariable String id) throws TableException{
		if(!tableOrders.containsKey(id))
			throw new TableException("Order " + id + " does not exists");
		tableOrders.remove(id);
	}
}
