package moe.src.leyline.interfaces.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.website.WebsiteRelation;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/19/16.
 */
@Data public class WebsiteDTO implements LeylineDTO {

    private String id;

    private Long createdAt;

    private Object description;

    private Object domain;

    private Long modifiedAt;

    private Object screenshot;

    private Object title;

    private String username;

}
