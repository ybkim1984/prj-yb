
package namoo.board.dom2.pr.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>findUserWithEmailResponse complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="findUserWithEmailResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://proxy.ws.pr.dom2.board.namoo/}dcBoardUser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findUserWithEmailResponse", propOrder = {
    "_return"
})
public class FindUserWithEmailResponse {

    @XmlElement(name = "return")
    protected DcBoardUser _return;

    /**
     * return 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link DcBoardUser }
     *     
     */
    public DcBoardUser getReturn() {
        return _return;
    }

    /**
     * return 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link DcBoardUser }
     *     
     */
    public void setReturn(DcBoardUser value) {
        this._return = value;
    }

}
