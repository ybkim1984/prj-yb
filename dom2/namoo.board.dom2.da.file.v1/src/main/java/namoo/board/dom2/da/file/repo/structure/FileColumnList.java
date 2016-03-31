/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.structure;

import java.util.ArrayList;
import java.util.List;

public class FileColumnList {
    //
    private List<FileColumn> columns = new ArrayList<FileColumn>();
    
    //--------------------------------------------------------------------------
    
    public void addColumn(String columnName, Integer dataColumnSeq) {
        //
        columns.add(new FileColumn(columnName, dataColumnSeq));
    }
    
    public FileColumn getColumn(String columnName) {
        //
        for (FileColumn fileColumn : columns) {
            //
            if (fileColumn.getName().equals(columnName)) {
                return fileColumn;
            }
        }
        return null;
    }

    //--------------------------------------------------------------------------
    
    public List<FileColumn> getColumns() {
        return columns;
    }
    
 
}
