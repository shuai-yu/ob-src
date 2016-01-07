package com.omnibounce.share;

import java.io.File;

public interface IShareContent {
	public File getEnvironmentExtenalPath();

	public boolean share(final String message, final String snapFile);

	public void throwToast(String msg);
}
