
package namoo.board.dom2.pr.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registerUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registerUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="boardUserJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerUser", propOrder = {
    "boardUserJson"
})
public class RegisterUser {

    protected String boardUserJson;

    /**
     * Gets the value of the boardUserJson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoardUserJson() {
        return boardUserJson;
    }

    /**
     * Sets the value of the boardUserJson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoardUserJson(String value) {
        this.boardUserJson = value;
    }

}
