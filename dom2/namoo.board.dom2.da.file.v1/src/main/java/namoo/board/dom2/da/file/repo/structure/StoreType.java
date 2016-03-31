/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.structure;



public enum StoreType {
    //
    /**
     * 저장소이름, 파일의 저장 데이터 컬럼명   
     */
    Sequence("board_seq", new String[] {"seqName", "nextSeq"}),
    User("board_user", new String[] {"email", "name", "phoneNumber"}),
    Team("board_team", new String[] {"usid", "name", "adminEmail"}),
    Member("board_member", new String[] {"usid", "teamUsid", "memberEmail"}),
    Board("social_board", new String[] {"usid", "name", "commentable", "teamUsid", "createDate"}),
    Posting("posting", new String[] {"usid", "boardUsid", "title", "writerEmail", "readCount", "regDate", "commentable", "anonymousComment", "contents"}),
    CommentBag("posting_comment_bag", new String[] {"postingUsid", "commentBagJson"});
    
    
    private String storeName;
    private FileColumnList fileColumns;
    
    //--------------------------------------------------------------------------
    
    StoreType(String storeName, String[] dataColumnsName) {
        //
        this.storeName = storeName;
        
        FileColumnList columns = new FileColumnList();
        
        for (int i = 0; i < dataColumnsName.length; i ++) {
            //
            String columnName = dataColumnsName[i];
            int dataColumnSeq = i;
            
            columns.addColumn(columnName, dataColumnSeq);
        }
        
        this.fileColumns = columns;
    }
    
    //--------------------------------------------------------------------------
    
    public String storeName() {
        return this.storeName;
    }

    public FileColumnList fileColumns() {
        return fileColumns;
    }
    
}
