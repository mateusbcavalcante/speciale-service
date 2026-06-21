package br.com.a2dm.spdm.omie.payload;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoWebhookPayload {

	private String messageId;
	private String topic;
	private ProdutoEventPayload event;
	private AuthorPayload author;
	private String appKey;
	private String appHash;
	private String origin;
	private String ping;

	public ProdutoWebhookPayload() {
		super();
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public ProdutoEventPayload getEvent() {
		return event;
	}

	public void setEvent(ProdutoEventPayload event) {
		this.event = event;
	}

	public AuthorPayload getAuthor() {
		return author;
	}

	public void setAuthor(AuthorPayload author) {
		this.author = author;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppHash() {
		return appHash;
	}

	public void setAppHash(String appHash) {
		this.appHash = appHash;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPing() {
		return ping;
	}

	public void setPing(String ping) {
		this.ping = ping;
	}
}
