package com.javieranton;


/**
 * 
 *  @author user
 */
public interface ProgressUploaderNativeInterface extends com.codename1.system.NativeInterface {

	public void PostMultipart(String url, String fileContent);
}
