package moe.src.leyline.business.service;

import javaslang.collection.List;
import org.springframework.stereotype.Service;

import moe.src.leyline.business.domain.topic.Topic;
import moe.src.leyline.business.domain.topic.TopicRepo;
import moe.src.leyline.framework.service.LeylineDomainService;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bytenoob on 6/19/16.
 */
@Service
public class TopicService extends LeylineDomainService<TopicRepo,Topic> {

}
