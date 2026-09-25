import entities.Adventurer;
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
          // Lê a opção como String e converte para número para evitar o bug clássico do
          // nextInt()
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
            System.out.println("Your Health Potion is being used...");

            try {
              // Tenta usar o item
              adventurer.useItem("Health Potion");
            } catch (ItemNotFoundException e) {
              // Se o item não existir, a exceção é capturada e a mensagem de erro é exibida
              System.out.println("\n> [ERROR] " + e.getMessage());
            }

          } else if (choice == 3) {
            System.out.println("\n> You ran away like a coward...");
            gameRunning = false;
          } else {
            System.out.println("\n> Invalid choice. Please pick 1, 2, or 3.");
          }

        } catch (NumberFormatException e) {
          // Tratamento de entrada inválida
          System.out.println("\n> INVALID INPUT! Please type a number.");
        }
      }
      // Verifica o estado final do jogo e exibe a mensagem final
      if (adventurer.getLife() <= 0) {
        System.out.println("\n>>> YOU DIED! GAME OVER <<<");
      } else if (goblin.getLife() <= 0) {
        System.out.println("\n>>> YOU WON THE BATTLE! <<<");
        System.out.println("> You picked up the " + goblinHead.getName() + ".");

        adventurer.getInventory().insertItem(goblinHead);

        System.out.println("\n> Returning to the village...");

        adventurer.completeQuest(clearCave);

        System.out.println("\n> Final Hero Status:");
        System.out.println("Coins: " + adventurer.getCoins());
        System.out.println("Inventory:\n" + adventurer.getInventory().toString());
      }
    }
  }
}
