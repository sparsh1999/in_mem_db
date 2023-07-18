package org.example.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class RowItem {
    int rowId;
    final List<Object> values = new ArrayList<>();
    long createdAt;
    long updatedAt;

    public RowItem(List<Object> values, int rowId){
        this.rowId = rowId;
        this.values.addAll(values);
        this.createdAt = System.currentTimeMillis();
    }
}
