/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.entities;



import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.InputStream;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;

public class translator {
  private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
  private static final String CLIENT_SECRET = "PUBLIC_SECRET";
  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

  /**
   * Sends a POST request to the WhatsMate Translation API to translate text.
   *
   * @param fromLang the source language
   * @param toLang the target language
   * @param text the text to translate
   * @throws Exception if an error occurs while sending the request
   */
  public static String translate(String fromLang, String toLang, String text)  {
    // Build the JSON payload
    String jsonPayload = new StringBuilder()
      .append("{")
      .append("\"fromLang\":\"")
      .append(fromLang)
      .append("\",")
      .append("\"toLang\":\"")
      .append(toLang)
      .append("\",")
      .append("\"text\":\"")
      .append(text)
      .append("\"")
      .append("}")
      .toString();

    // Create the connection request and set the request properties
    ConnectionRequest request = new ConnectionRequest();
    request.setUrl(ENDPOINT);
    request.setPost(true);
    request.addRequestHeader("X-WM-CLIENT-ID", CLIENT_ID);
    request.addRequestHeader("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    request.addRequestHeader("Content-Type", "application/json");

    // Write the JSON payload to the request body
    request.setRequestBody(jsonPayload);

    // Send the request and read the response from the API
    NetworkManager.getInstance().addToQueueAndWait(request);
    String output = new String(request.getResponseData());

    // Print the output to the console
    System.out.println(output);
    return output;
  }
}

