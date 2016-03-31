/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repository.structure;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.da.file.repository.structure.constant.FileConst;

public class ColumnValueList {
    //
    private StoreType storeType;
    private List<ColumnValue> columnValues = new ArrayList<ColumnValue>();

    
    public ColumnValueList(StoreType storeType) {
        //
        this.storeType = storeType;
    }
    
    //--------------------------------------------------------------------------
    
    public void addColumnValue(String columnName, String value) {
        //
        FileColumnList fileColumns = storeType.fileColumns();
        FileColumn column = fileColumns.getColumn(columnName);
        
        columnValues.add(new ColumnValue(column, value));
    }
    
    public void addColumnValue(String columnName, int value) {
        //
        addColumnValue(columnName, String.valueOf(value));
    }
    
    public void addColumnValue(String columnName, boolean value) {
        //
        addColumnValue(columnName, String.valueOf(value));
    }
    
    public ColumnValue getKeyColumnValue() {
        //
        if (columnValues == null || columnValues.isEmpty()) {
            return null;
        }
        return columnValues.get(0);
    }
    
    public boolean checkEqualsAllColumnValue(String[] datas) {
        //
        boolean equalsAll = true;
        
        for (ColumnValue columnValue : columnValues) {
            String data = datas[columnValue.getDataColumnSeq()];
            
            // 값이 하나라도 일치하지 않으면 false 반환
            if (!columnValue.getValue().equals(data)) {
                equalsAll = false;
                break;
            }
        }
        return equalsAll;
    }
    
    public String getDataCsv() {
        //
        StringBuilder csv = new StringBuilder();
        int lastIndex = columnValues.size() - 1;
        
        
        for (int i = 0; i < columnValues.size(); i ++) {
            //
            csv.append(columnValues.get(i).getValue());
            
            if (i != lastIndex) {
                csv.append(FileConst.SEPARATOR);
            }
        }
        csv.append(FileConst.NEW_LINE);
        
        return csv.toString();
    }
    
    //--------------------------------------------------------------------------
    
    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }
    
}
