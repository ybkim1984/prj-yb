/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.structure;

public class FileColumn {
    //
    private String name;
    private Integer dataColumnSeq;
    
    //--------------------------------------------------------------------------
    
    public FileColumn(String name, Integer dataColumnSeq) {
        //
        this.name = name;
        this.dataColumnSeq = dataColumnSeq;
    }
    
    //--------------------------------------------------------------------------

    public String getName() {
        return name;
    }
    
    public Integer getDataColumnSeq() {
        return dataColumnSeq;
    }
}
