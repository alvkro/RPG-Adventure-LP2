package com.rpgadventure;

import com.rpgadventure.Reward;

public class Mission {
  private final String title;
  private final Reward reward;
  private boolean is_done = false;

  public Mission(String title, Reward reward) {
    this.title = title;
    this.reward = reward;
  }

  public Reward doMission() {
    is_done = true;

    return reward;
  }

  @Override
  public String toString() {
    return "Título: " + this.title + "\nRecompensa: " + reward +
        "\nConcluída" + (is_done ? "Sim" : "Não");
  }
}
