public class Personagem {
  private String name;
  private int level;
  private int coins;

  public Personagem(String name, int coins) {
    this.name = name;
    this.coins = coins;
    this.level = 1;
  }

  public int getCoins() {
    return this.coins;
  }

  public void dialogue() {
    System.out.println("Talking...");
  }

  public void receive(Recompensa r) {
    this.coins += r.getCoin();
  }
}
