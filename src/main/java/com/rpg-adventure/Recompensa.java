public class Recompensa {
  private String description;
  private final int coin;

  public Recompensa(String description, int coin) {
    this.description = description;
    this.coin = coin;
  }

  public int getCoin() {
    return coin;
  }
}
