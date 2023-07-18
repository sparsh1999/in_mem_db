package org.example.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Table {
    String tableName;
    List<TableHeader> tableHeaders;
    List<RowItem> rows;
    long createdTs;
    int nextRowId;

    public Table(String tableName, List<TableHeader> tableHeaders) {
        this.tableHeaders = tableHeaders;
        this.tableName = tableName;
        this.rows = new ArrayList<>();
        this.createdTs = System.currentTimeMillis();
        this.nextRowId = 0;
    }

    public void addRow(RowItem rowItem) {
        this.rows.add(rowItem);
        this.nextRowId++;
    }

    public boolean deleteRow(int rowId) {
        Optional<RowItem> optionalRowToDelete = this.rows.stream().filter(row -> row.getRowId()==rowId).findAny();
        if (optionalRowToDelete.isEmpty()){
            throw new RuntimeException("Row does not exist");
        }
        this.rows.remove(optionalRowToDelete.get());
        return true;
    }

    public int columnCount() {
        return this.tableHeaders.size();
    }
}
