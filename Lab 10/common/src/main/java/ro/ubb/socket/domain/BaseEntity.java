package ro.ubb.socket.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author radu.
 */
public class BaseEntity<ID> {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{ " +
                "id=" + id +
                '}';
    }
}
