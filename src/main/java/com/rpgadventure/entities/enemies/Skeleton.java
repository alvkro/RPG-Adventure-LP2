package entities.enemies;

public class Skeleton extends Enemy {
    public Skeleton(String name, int coins) {
        super(name, coins);
    }

    @Override
    public int attack() {
        System.out.println("*Barulhos de esqueleto...*");
        System.out.println("*Barulhos de... FLECHA!*");
        return this.getDamage();
    }
}
