/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo.structure;

public enum SequenceType {
    
    Team("team"),
    Board("board"),
    Posting("posting", "-posting"),
    Comment("comment", "-comment");
    
    private String sequenceName;
    private String suffix;
    
    //--------------------------------------------------------------------------
    
    SequenceType(String sequenceName) {
        //
        this.sequenceName = sequenceName;
    }
    SequenceType(String sequenceName, String suffix) {
        //
        this.sequenceName = sequenceName;
        this.suffix = suffix;
    }
    
    //--------------------------------------------------------------------------
    
    public String sequenceName() {
        return sequenceName;
    }
    public String suffix() {
        return suffix;
    }
}
