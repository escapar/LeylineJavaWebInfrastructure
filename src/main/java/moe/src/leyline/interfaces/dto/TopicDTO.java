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
import moe.src.leyline.business.domain.topic.Topic;
import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/19/16.
 */
@Data public class TopicDTO implements LeylineDTO{

    private String id;

    private int status;

    private String title;

    private int type;

    private Topic topic;

    private String username;
}
