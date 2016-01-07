package com.omnibounce.share;

public class ShareContent {

	private static IShareContent content;

	public static IShareContent getContent() {
		return content;
	}

	public static void setContent(IShareContent content) {
		ShareContent.content = content;
	}
	
}
