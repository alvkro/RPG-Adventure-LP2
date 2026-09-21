package entities.enemies;

public class Goblin extends Enemy {
    public Goblin(String name, int coins) {
        super(name, coins);
    }

    @Override
    public int attack() {
        System.out.println("Hahaha sou um Goblin malvado atacando!!!");
        return this.getDamage();
    }
}
