package com.xinlab.blueapple.contenttool.bean;


public class FeedMsg {
	private String feed_id;
	private String feed_title;
	public String getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}
	public String getFeed_title() {
		return feed_title;
	}
	public void setFeed_title(String feed_title) {
		this.feed_title = feed_title;
	}
	public FeedMsg() {
	}
	public FeedMsg(String feed_id, String feed_title) {
		super();
		this.feed_id = feed_id;
		this.feed_title = feed_title;
	}
}
