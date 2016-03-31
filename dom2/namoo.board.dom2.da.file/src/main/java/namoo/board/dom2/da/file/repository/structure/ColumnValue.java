/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repository.structure;

public class ColumnValue {
    //
    private FileColumn column;
    private String value;
    
    public ColumnValue(FileColumn column, String value) {
        //
        this.column = column;
        this.value = value;
    }
    
    
    public Integer getDataColumnSeq() {
        return this.column.getDataColumnSeq();
    }
    
    public FileColumn getColumn() {
        //
        return column;
    }
    
    public String getValue() {
        return this.value;
    }
}
