package moe.src.leyline.interfaces.rest;

import moe.src.leyline.business.domain.topic.Topic;
import moe.src.leyline.business.service.TopicService;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.topic.TopicDTO;

/**
 * Created by bytenoob on 6/19/16.
 */
public class TopicAPI extends LeylineRestCRUD<TopicService, Topic, TopicDTO> {
}
