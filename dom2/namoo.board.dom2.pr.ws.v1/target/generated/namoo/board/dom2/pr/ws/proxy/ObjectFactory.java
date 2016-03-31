
package namoo.board.dom2.pr.ws.proxy;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the namoo.board.dom2.pr.ws.proxy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindAllUsers_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "findAllUsers");
    private final static QName _FindUserWithEmailResponse_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "findUserWithEmailResponse");
    private final static QName _RegisterUser_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "registerUser");
    private final static QName _RemoveUserWithEmailResponse_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "removeUserWithEmailResponse");
    private final static QName _FindAllUsersResponse_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "findAllUsersResponse");
    private final static QName _RegisterUserResponse_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "registerUserResponse");
    private final static QName _RemoveUserWithEmail_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "removeUserWithEmail");
    private final static QName _FindUserWithEmail_QNAME = new QName("http://proxy.ws.pr.dom2.board.namoo/", "findUserWithEmail");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: namoo.board.dom2.pr.ws.proxy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DcBoardUser }
     * 
     */
    public DcBoardUser createDcBoardUser() {
        return new DcBoardUser();
    }

    /**
     * Create an instance of {@link FindUserWithEmail }
     * 
     */
    public FindUserWithEmail createFindUserWithEmail() {
        return new FindUserWithEmail();
    }

    /**
     * Create an instance of {@link FindUserWithEmailResponse }
     * 
     */
    public FindUserWithEmailResponse createFindUserWithEmailResponse() {
        return new FindUserWithEmailResponse();
    }

    /**
     * Create an instance of {@link RegisterUserResponse }
     * 
     */
    public RegisterUserResponse createRegisterUserResponse() {
        return new RegisterUserResponse();
    }

    /**
     * Create an instance of {@link RegisterUser }
     * 
     */
    public RegisterUser createRegisterUser() {
        return new RegisterUser();
    }

    /**
     * Create an instance of {@link FindAllUsers }
     * 
     */
    public FindAllUsers createFindAllUsers() {
        return new FindAllUsers();
    }

    /**
     * Create an instance of {@link RemoveUserWithEmailResponse }
     * 
     */
    public RemoveUserWithEmailResponse createRemoveUserWithEmailResponse() {
        return new RemoveUserWithEmailResponse();
    }

    /**
     * Create an instance of {@link FindAllUsersResponse }
     * 
     */
    public FindAllUsersResponse createFindAllUsersResponse() {
        return new FindAllUsersResponse();
    }

    /**
     * Create an instance of {@link RemoveUserWithEmail }
     * 
     */
    public RemoveUserWithEmail createRemoveUserWithEmail() {
        return new RemoveUserWithEmail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "findAllUsers")
    public JAXBElement<FindAllUsers> createFindAllUsers(FindAllUsers value) {
        return new JAXBElement<FindAllUsers>(_FindAllUsers_QNAME, FindAllUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserWithEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "findUserWithEmailResponse")
    public JAXBElement<FindUserWithEmailResponse> createFindUserWithEmailResponse(FindUserWithEmailResponse value) {
        return new JAXBElement<FindUserWithEmailResponse>(_FindUserWithEmailResponse_QNAME, FindUserWithEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "registerUser")
    public JAXBElement<RegisterUser> createRegisterUser(RegisterUser value) {
        return new JAXBElement<RegisterUser>(_RegisterUser_QNAME, RegisterUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserWithEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "removeUserWithEmailResponse")
    public JAXBElement<RemoveUserWithEmailResponse> createRemoveUserWithEmailResponse(RemoveUserWithEmailResponse value) {
        return new JAXBElement<RemoveUserWithEmailResponse>(_RemoveUserWithEmailResponse_QNAME, RemoveUserWithEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "findAllUsersResponse")
    public JAXBElement<FindAllUsersResponse> createFindAllUsersResponse(FindAllUsersResponse value) {
        return new JAXBElement<FindAllUsersResponse>(_FindAllUsersResponse_QNAME, FindAllUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "registerUserResponse")
    public JAXBElement<RegisterUserResponse> createRegisterUserResponse(RegisterUserResponse value) {
        return new JAXBElement<RegisterUserResponse>(_RegisterUserResponse_QNAME, RegisterUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserWithEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "removeUserWithEmail")
    public JAXBElement<RemoveUserWithEmail> createRemoveUserWithEmail(RemoveUserWithEmail value) {
        return new JAXBElement<RemoveUserWithEmail>(_RemoveUserWithEmail_QNAME, RemoveUserWithEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserWithEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://proxy.ws.pr.dom2.board.namoo/", name = "findUserWithEmail")
    public JAXBElement<FindUserWithEmail> createFindUserWithEmail(FindUserWithEmail value) {
        return new JAXBElement<FindUserWithEmail>(_FindUserWithEmail_QNAME, FindUserWithEmail.class, null, value);
    }

}
