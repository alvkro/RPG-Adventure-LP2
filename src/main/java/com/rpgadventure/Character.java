package com.rpgadventure;

import java.util.ArrayList;
import main.java.com.rpgadventure.ItemType;

public class Character {
  private String name;
  private int energy;
  private int level;
  private int coins;
  private ArrayList<ItemType> inventory;

  public Character(String name, int coins) {
    this.name = name;
    this.coins = coins;
    this.level = 1;
    this.inventory = new ArrayList<ItemType>(10);
  }

  public String getName() { return name; }
  public int getLevel() { return level; }
  public int getCoins() {
    return this.coins;
  }
  public int getEnergy() { return this.energy; }
  public ArrayList<ItemType> getInventory() {
    return inventory;
  }

  public ItemType GetItemFromInvetory(int idx) { return getInventory().get(idx); }

  public void dialogue() {
    System.out.println("Talking...");
  }

  public void receive(Reward r) {
    this.coins += r.getCoin();
  }
}
