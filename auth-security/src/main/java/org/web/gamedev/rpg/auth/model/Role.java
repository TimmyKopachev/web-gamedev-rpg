package org.web.gamedev.rpg.auth.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
@ToString(exclude = "users")
public class Role extends IdEntity {

  @Column(name = "name")
  private String name;

  @ManyToMany(
      mappedBy = "roles",
      fetch = FetchType.EAGER,
      cascade = {CascadeType.ALL})
  private List<User> users;
}
