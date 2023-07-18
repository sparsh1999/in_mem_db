package org.example.service;

import lombok.Getter;
import org.example.models.RowItem;
import org.example.models.Table;
import org.example.models.TableHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableService {
    @Getter
    Map<String, Table> tableMap;

    public TableService() {
        this.tableMap = new HashMap<>();
    }

    public void createTable(String tableName, List<TableHeader> tableHeaders) {
        if (tableMap.containsKey(tableName)) {
            throw new RuntimeException("Table exist");
        }
        Table table = new Table(tableName, tableHeaders);
        tableMap.put(tableName, table);
    }


    public void addRow(String tableName, List<Object> values) {
        Table table = tableMap.get(tableName);
        validate(table, values);
        RowItem rowItem = new RowItem(values, table.getNextRowId());
        table.addRow(rowItem);
    }

    public boolean deleteRow(String tableName, int rowId) {
        Table table = tableMap.get(tableName);
        return table.deleteRow(rowId);
    }

    public List<String> getAllTableName(){
        return new ArrayList<>(tableMap.keySet());
    }

    public List<RowItem> getAllRowsInTable(String tableName) {
        Table table = tableMap.get(tableName);
        return new ArrayList<>(table.getRows());
    }

    private void validate(Table table, List<Object> values) {
        if (table.columnCount() == values.size()) {
            for (int i =0;i<values.size();i++){
                TableHeader header = table.getTableHeaders().get(i);
                Object value = values.get(i);
                switch(header.getConstraint()){
                    case INT_RANGE_0_1000:
                        int convertedInt = (int) value;
                        if (!(convertedInt>=0 && convertedInt <= 1000))
                            throw new RuntimeException(String.format("Int Constraint check failed for value: %d", convertedInt));
                        break;
                    case STRING_LENGTH_LTE_2000:
                        String convertedString = (String) value;
                        if (!(convertedString.length()<20))
                            throw new RuntimeException(String.format("String Constraint check failed for value: %s", convertedString));
                        break;
                    case NONE:
                        break;
                    default:
                        throw new RuntimeException("Constraint not defined");
                }
            }
        }
    }

}