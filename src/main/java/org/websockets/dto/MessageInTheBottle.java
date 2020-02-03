package org.websockets.dto;

public class MessageInTheBottle {

  private String name;
  private String text;

  public void setName(String name) {
    this.name = name;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

}
