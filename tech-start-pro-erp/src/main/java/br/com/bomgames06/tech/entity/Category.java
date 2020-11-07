package br.com.bomgames06.tech.entity;

import br.com.bomgames06.tech.entity.generic.GenericEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "tb_category", schema = "public")
@XmlRootElement
public class Category implements GenericEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Category")
    @SequenceGenerator(name = "Category", sequenceName = "seq_id_category_category", allocationSize = 1)
    @Column(name = "id_category", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 33)
    private String name;

    public Category() {}

    @Override
    public Long getCode() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
