package br.com.bomgames06.tech.entity;

import br.com.bomgames06.tech.entity.generic.GenericEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_product", schema = "public")
public class Product implements GenericEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Product")
    @SequenceGenerator(name = "Product", sequenceName = "seq_id_product_product", allocationSize = 1)
    @Column(name = "id_product", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 33)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tb_category_product", joinColumns={@JoinColumn(name="id_product")}, inverseJoinColumns={@JoinColumn(name="id_category")})
    private List<Category> categories;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
