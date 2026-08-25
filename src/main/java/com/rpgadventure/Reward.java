package com.rpgadventure;

public class Reward {
  private String description;
  private final int coin;

  public Reward(String description, int coin) {
    this.description = description;
    this.coin = coin;
  }

  public int getCoin() {
    return coin;
  }
}
