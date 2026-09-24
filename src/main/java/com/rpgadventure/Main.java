import entities.Adventurer;
import entities.*;
import entities.enemies.*;
import equipment.*;
import quests.Quest;
import quests.Reward;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    // Try-with-resources aplicado no Scanner
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("\n>>> WELCOME TO YOUR ADVENTURE! <<<");

      // Instanciação de itens, recompensas e missões

      Item goblinHead = new Collectible("Goblin's Head", ItemType.COLLECTIBLE, Rarity.COMMON);
      Item healthPotion = new Potion("Health Potion", ItemType.CONSUMABLE, Rarity.COMMON, 50);
      Item ironSword = new Weapon("Iron Sword", ItemType.EQUIPMENT, Rarity.RARE, 25);

      Reward questReward = new Reward("quests.Quest quests.Reward", 150, ironSword);
      Enemy goblin = new Goblin("Evil goblin", 50);

      Quest clearCave = new Quest(
          "Clear The Cave",
          "Kill the evil goblin in the cave and bring its head as proof.",
          questReward,
          goblinHead,
          goblin);

      // Instanciação de um aventureiro
      System.out.print("> Please, enter your adventurer's name: ");

      String adventurerName = scanner.nextLine();

      Adventurer adventurer = new Adventurer(adventurerName, 100);

      // Mensagem de boas-vindas e início da aventura
      System.out
          .println("> Welcome, " + adventurer.getName() + "! You have "
              + adventurer.getCoins() + " coins in your pocket.\n"
              + "> Before setting out, you also got a Health Potion!");

      // Adicionando o item Health Potion ao inventário do aventureiro
      adventurer.getInventory().insertItem(healthPotion);

      boolean gameRunning = true;

      // Aventureiro aceita a missão que fica na lista de missões ativas
      adventurer.acceptQuest(clearCave);

      System.out.println("\n>> A wild " + goblin.getName() + " appears! Prepare for battle!");

      // Loop principal do Jogo
      while (gameRunning && adventurer.getLife() > 0 && goblin.getLife() > 0) {

        System.out.println("\n==================================");
        System.out.println(adventurer.getName() + " HP: " + adventurer.getLife());
        System.out.println(goblin.getName() + " HP: " + goblin.getLife());
        System.out.println("==================================");
        System.out.println("What will you do?");
        System.out.println("[1] Attack");
        System.out.println("[2] Use Health Potion");
        System.out.println("[3] Run away");
        System.out.print("> Choice: ");

        try {
          // Lê a opção como String e converte para número para evitar o bug clássico do nextInt()
          int choice = Integer.parseInt(scanner.nextLine());

          if (choice == 1) {
            // Aventureiro ataca
            System.out.println("\n> You attack the " + goblin.getName() + "!");
            goblin.receiveDmg(adventurer.getDamage());

            // Se o goblin não morreu, ele revida
            if (goblin.getLife() > 0) {
               adventurer.receiveDmg(goblin.attack()); 
            }

          } else if (choice == 2) {
            System.out.println("\n> " + adventurer.getInventory().toString());
            System.out.println("Type the exact name of the item to use:");
            String itemName = scanner.nextLine();
            
            try {
                // Tenta usar o item
                adventurer.getInventory().useItem(itemName);
            } catch (ItemNotFoundException e) {
                // Se o item não existir, sua exceção é disparada e o erro aparece aqui!
                System.out.println("\n> [AVISO] " + e.getMessage());
            }

          } else if (choice == 3) {
            System.out.println("\n> You ran away like a coward...");
            gameRunning = false;
          } else {
            System.out.println("\n> Invalid choice. Please pick 1, 2, or 3.");
          }

        } catch (NumberFormatException e) {
          // Tratamento de entrada inválida (ex: digitar 'A' no menu numérico) - Requisito 0,40 pt
          System.out.println("\n> INVALID INPUT! Please type a number.");
        }
      }

        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("NO")) {
          System.out.println("> You ignore the old man and walk away. The goblin destroys the village later.");
          System.out.println("\n>>> GAME OVER <<<");
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
          System.out.println("\n>>> GAME OVER <<<");
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
        System.out.println(">> As a reward for being a brave hero, we'll show you "
            + "how the program works!");
        System.out.println("> Exhibiting validation that prevents invalid states...");

        Item testItem = new Collectible("Dragon Scale", ItemType.COLLECTIBLE, Rarity.LEGENDARY);
        System.out.println("> Attempting to remove an item the adventurer doesn't have (" + testItem.getName() + ").");

        try {
          adventurer.getInventory().removeItem(testItem);
        } catch (IllegalStateException e) {
          System.out.println("[EXCEPTION CAUGHT]: " + e.getMessage());
        }

        System.out.println("\n> Final Hero Status:");
        System.out.println("Coins: " + adventurer.getCoins());
        System.out.println("equipment.Inventory:\n" + adventurer.getInventory().toString());
      }
    }
  }
}
