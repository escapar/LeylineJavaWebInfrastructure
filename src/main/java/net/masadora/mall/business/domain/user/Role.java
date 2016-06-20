package net.masadora.mall.business.domain.user;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by POJO on 6/20/16.
 */
@Entity
@Data
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
