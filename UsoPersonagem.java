public class UsoPersonagem {
  public static void main(String[] args) {
    Personagem p = new Personagem("álvaro", 20);
    Recompensa r = new Recompensa("saco de moedas", 30);

    p.receive(r);

    System.out.println(p.getCoins());
  }
}
