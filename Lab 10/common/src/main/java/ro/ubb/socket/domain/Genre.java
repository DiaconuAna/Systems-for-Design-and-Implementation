package ro.ubb.socket.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class Genre extends BaseEntity<Long>
{
    private String name;
    private String description;

    public Genre(){
        this.name = "";
        this.description = "";
        setId(Long.parseLong("0"));
    }

    public Genre(Long id, String name, String description) {
        setId(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Genre { id = %d, name = %s, description = %s }", getId(), getName(), getDescription());
    }
}
