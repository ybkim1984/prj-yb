/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 2. 3.
 */
package namoo.board.dom2.da.hibernate.jpao.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TB_BOARD_SEQ")
public class BoardSeqJpao implements Serializable {
    //
    private static final long serialVersionUID = 8605784414721857876L;
    
    @Id
    @Column(name="seq_name")
    private String seqName;
    
    @Column(name="next_seq")
    private int nextSeq;
    
    public BoardSeqJpao() {
        super();
    }

    public BoardSeqJpao(String name, int currentSeq) {
        // 
        this.seqName = name;
        this.nextSeq = currentSeq;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public int getNextSeq() {
        return nextSeq;
    }

    public void setNextSeq(int nextSeq) {
        this.nextSeq = nextSeq;
    }
}
