package org.example;

import static org.junit.Assert.assertEquals;

import org.example.models.RowItem;
import org.example.models.TableHeader;
import org.example.service.TableService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryDbTestApplication {

    TableService tableService;

    @Before
    public void setup(){
        tableService = new TableService();
    }

    @Test
    public void createTable(){
        tableService.createTable("employee", Arrays.asList(
                new TableHeader("Id", TableHeader.Constraint.INT_RANGE_0_1000, TableHeader.ColumnType.INT),
                new TableHeader("Name", TableHeader.Constraint.STRING_LENGTH_LTE_2000, TableHeader.ColumnType.STRING)
        ));
        assertEquals(tableService.getAllTableName(), List.of("employee"));
    }

    @Test
    public void testInsertInDb() {
        tableService.createTable("employee", Arrays.asList(
                new TableHeader("Id", TableHeader.Constraint.INT_RANGE_0_1000, TableHeader.ColumnType.INT),
                new TableHeader("Name", TableHeader.Constraint.STRING_LENGTH_LTE_2000, TableHeader.ColumnType.STRING)
        ));
        tableService.addRow("employee", Arrays.asList(10, "Sparsh"));
        tableService.addRow("employee", Arrays.asList(11, "Amit"));

        List<String> persistedRows = tableService.getAllRowsInTable("employee").stream()
                .map(RowItem::getValues).map(values -> (String)values.get(1)).collect(Collectors.toList());

        assertEquals(persistedRows, List.of("Sparsh", "Amit"));
    }

    @Test
    public void testDeleteRow() {
        tableService.createTable("employee", Arrays.asList(
                new TableHeader("Id", TableHeader.Constraint.INT_RANGE_0_1000, TableHeader.ColumnType.INT),
                new TableHeader("Name", TableHeader.Constraint.STRING_LENGTH_LTE_2000, TableHeader.ColumnType.STRING)
        ));
        tableService.addRow("employee", Arrays.asList(10, "Sparsh"));
        tableService.addRow("employee", Arrays.asList(11, "Amit"));

        assertEquals(tableService.getAllRowsInTable("employee").size(), 2);

        tableService.deleteRow("employee", 1);
        assertEquals(tableService.getAllRowsInTable("employee").size(), 1);
    }

    @Test
    public void testInsertConstraintCheck() {
        tableService.createTable("employee", Arrays.asList(
                new TableHeader("Id", TableHeader.Constraint.INT_RANGE_0_1000, TableHeader.ColumnType.INT),
                new TableHeader("Name", TableHeader.Constraint.STRING_LENGTH_LTE_2000, TableHeader.ColumnType.STRING)
        ));

        try {
            tableService.addRow("employee", Arrays.asList(1043, "Amit"));
        } catch (Exception e){
            assertEquals(e.getMessage(), "Int Constraint check failed for value: 1043");
        }

        try {
            tableService.addRow("employee", Arrays.asList(10, "Amit and hello dummy values which are out of scope"));
        } catch (Exception e){
            assertEquals(e.getMessage(), "String Constraint check failed for value: Amit and hello dummy values which are out of scope");
        }
    }

    @After
    public void cleanUp() {
        System.out.println("Cleanup");
        tableService.getTableMap().clear();
    }



}
