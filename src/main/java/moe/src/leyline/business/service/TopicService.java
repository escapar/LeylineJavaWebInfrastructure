package moe.src.leyline.business.service;

import org.springframework.stereotype.Service;

import moe.src.leyline.business.domain.topic.Topic;
import moe.src.leyline.business.domain.topic.TopicRepo;
import moe.src.leyline.framework.service.LeylineTransactionalService;

/**
 * Created by bytenoob on 6/19/16.
 */
@Service
public class TopicService extends LeylineTransactionalService<TopicRepo, Topic> {

}
