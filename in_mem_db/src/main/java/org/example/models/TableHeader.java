package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TableHeader {
    String columnName;
    Constraint constraint;
    ColumnType columnType;

    public enum Constraint{
        INT_RANGE_0_1000, STRING_LENGTH_LTE_2000, NONE;
    }

    public static enum ColumnType{
        STRING, INT;
    }
}
