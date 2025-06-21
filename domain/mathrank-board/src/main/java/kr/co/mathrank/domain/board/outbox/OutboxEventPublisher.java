package kr.co.mathrank.domain.board.outbox;

import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import kr.co.mathrank.domain.board.entity.Post;
import kr.co.mathrank.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
	private final PostRepository postRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Async("eventAsyncPublisher")
	public void publishMessage(final Post post) {
		final Outbox outbox = post.getOutbox();
		try {
			kafkaTemplate.send(outbox.getEventType().getTopic(), outbox.getPayload())
				.get(1L, TimeUnit.SECONDS);
			post.clearOutbox();
			postRepository.save(post);
		} catch (Exception e) {
			log.error("[OutboxEventExecutor.publishMessage]: error occurred {}", post, e);
			throw new RuntimeException(e);
		}
	}
}
