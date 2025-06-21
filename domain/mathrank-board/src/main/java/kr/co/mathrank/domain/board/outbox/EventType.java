package kr.co.mathrank.domain.board.outbox;

import lombok.Getter;

@Getter
public enum EventType {
	POST_CREATED_EVENT(Topic.POST_CREATED_EVENT),
	POST_DELETED_EVENT(Topic.POST_DELETED_EVENT),
	POST_UPDATED_EVENT(Topic.POST_UPDATED_EVENT);

	private String topic;

	EventType(String topic) {
		this.topic = topic;
	}

	public static class Topic {
		private static final String POST_CREATED_EVENT = "post-created-event";
		private static final String POST_DELETED_EVENT = "post-deleted-event";
		private static final String POST_UPDATED_EVENT = "post-updated-event";
	}
}
