package com.ccclogic.fusion.entities.tenant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "portal_menu")
@AllArgsConstructor
@NoArgsConstructor
public class PortalMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "description")
    private String description;

    @Column(name = "icon")
    private String icon;

    @Column(name = "style")
    private String style;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type")
    private MenuType menuType;

    @Column(name = "external")
    private Boolean external;

    @Column(name = "link")
    private String link;

    @Column(name = "parent")
    private Long parent;

    @Column(name = "menu_order")
    private String menuOrder;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "access")
    private Long access;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent",
            nullable = true, insertable = false, updatable = false)
    @JsonIgnore
    private PortalMenu mainMenu;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mainMenu")
    private Set<PortalMenu> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortalMenu menu = (PortalMenu) o;
        return Objects.equal(id, menu.id) && menuType == menu.menuType && Objects.equal(link, menu.link) && Objects.equal(parent, menu.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, menuType, link, parent);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", external=" + external +
                ", link='" + link + '\'' +
                ", menuOrder='" + menuOrder + '\'' +
                ", active=" + active +
                ", access=" + access +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                '}';
    }

    enum MenuType {
        FOLDER, LINK
    }
}


