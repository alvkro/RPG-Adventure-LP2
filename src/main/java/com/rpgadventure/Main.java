import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    System.out.println(">>> WELCOME TO YOUR ADVENTURE! <<<");

    Item goblinHead = new Item("Goblin's Head", ItemType.COLLECTIBLE, Rarity.COMMON);
    Item healthPotion = new Item("Health Potion", ItemType.CONSUMABLE, Rarity.COMMON);
    Item ironSword = new Item("Iron Sword", ItemType.EQUIPMENT, Rarity.RARE);

    Reward questReward = new Reward("Quest Reward", 150, ironSword);
    Quest clearCave = new Quest(
        "Clear The Cave",
        "Kill the evil goblin in the cave and bring its head as proof.",
        questReward,
        goblinHead);

    Scanner scanner = new Scanner(System.in);
    System.out.print("> Please, enter your adventurer's name: ");
    String adventurerName = scanner.nextLine();

    Adventurer adventurer = new Adventurer(adventurerName, 100);

    System.out
        .println("> Welcome, " + adventurer.getName() + "! You have "
            + adventurer.getCoins() + " coins in your pocket.\n"
            + "> Before setting out, you also got a Health Potion!");
    adventurer.getInventory().insertItem(healthPotion);

    System.out.println(">>> YOUR ADVENTURE BEGINS! <<<");

    System.out.println("> Just as you set out your village, an old man asks for "
        + "your help by the road...");
    System.out.println("> \"Hey! You there, young man! Can you help me?\"");
    System.out.print("> Do you want to help him? (Y/N): ");
    String choice = scanner.nextLine();

    if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("NO")) {
      System.out.println("> You ignore the old man and walk away. The goblin destroys the village later.");
      System.out.println(">>> GAME OVER <<<");
      scanner.close();
      return;
    }

    System.out.println("> \"Oh, thank you! Please, clear the cave up ahead! "
        + "There's an evil goblin lurking there in the dark, and he's threatening "
        + "to destroy the village! Go, quick!\"");
    clearCave.obtainQuest();

    System.out.println(">> You find your way through the forest on quick feet to "
        + "confront the goblin.");
    System.out.println(">> As you enter the cave, a foul stench leads you through "
        + "the dark, forking tunnels and into the goblin's den. Something tells you "
        + "that this goblin is way too smart...");

    System.out.println(">> You see the goblin distracted, sharpening a rusty blade.");
    System.out.println("> How will you approach the goblin?");
    System.out.println("  [1] Charge blindly!");
    System.out.println("  [2] Sneak around the shadows...");
    System.out.print("> Your choice (1 or 2): ");

    String combatChoice = scanner.nextLine();

    if (combatChoice.equals("1")) {
      System.out.println(
          "> You yell and charge! The smart goblin anticipated this, triggers a hidden trap, and you are crushed by a falling rock.");
      System.out.println(">>> GAME OVER <<<");
      scanner.close();
      return;
    }

    System.out.println("> You sneak quietly behind the goblin and strike a fatal blow! The goblin is defeated!");

    adventurer.getInventory().insertItem(goblinHead);
    System.out.println("> [ITEM OBTAINED]: " + goblinHead.getName() + " added to inventory.");

    System.out.println("\n>> You return to the old man victorious and show him the head.");
    adventurer.completeQuest(clearCave);
    System.out.println("> \"Thank you, brave adventurer! Here is your reward!\"");
    System.out.println("> [ITEM OBTAINED]: " + questReward.getItemReward().getName() + " added to inventory.");

    System.out.println("\n>>> CONGRATULATIONS! YOU COMPLETED YOUR ADVENTURE! (for now...) <<<");

    System.out.println("\n>>> OTHER FUNCTIONALITIES <<<");
    System.out.println("> Exhibiting validation that prevents invalid states...");

    Item testItem = new Item("Dragon Scale", ItemType.COLLECTIBLE, Rarity.LEGENDARY);
    System.out.println("> Attempting to remove an item the adventurer doesn't have (" + testItem.getName() + ").");

    try {
      adventurer.getInventory().removeItem(testItem);
    } catch (IllegalStateException e) {
      System.out.println("[EXCEPTION CAUGHT]: " + e.getMessage());
    }

    System.out.println("\n> Final Hero Status:");
    System.out.println("Coins: " + adventurer.getCoins());
    System.out.println("Inventory:\n" + adventurer.getInventory().toString());

    scanner.close();
  }
}
