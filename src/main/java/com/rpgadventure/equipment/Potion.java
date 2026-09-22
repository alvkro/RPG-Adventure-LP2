package equipment;

public class Potion extends Item implements Usable {
    private final int treatment;

    public Potion(String name, ItemType type, Rarity rarity, int treatment){
        super(name, type, rarity);
        if (treatment < 0) {
            throw new IllegalArgumentException("Potion treatment cannot be negative.");
        }
        this.treatment = treatment;
    }

    @Override
    public void usar(Personagem personagem) {
        personagem.receberCura(treatment);
    }
}