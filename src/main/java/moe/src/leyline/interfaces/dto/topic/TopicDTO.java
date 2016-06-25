package moe.src.leyline.interfaces.dto.topic;

import lombok.Data;
import moe.src.leyline.business.domain.topic.Topic;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;

/**
 * Created by bytenoob on 6/19/16.
 */
@Data public class TopicDTO implements LeylineDTO {

    private String id;

    private int status;

    private String title;

    private int type;

    private Topic topic;

    private String username;
}
