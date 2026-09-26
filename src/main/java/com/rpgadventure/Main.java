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

      Item goblinHead = new Collectible("Goblin's Head", ItemType.COLLECTIBLE, Rarity.COMMON); // Item de missão
      Item skeletonBones = new Collectible("Skeleton's Bones", ItemType.COLLECTIBLE, Rarity.RARE); // Item de missão

      Item healthPotion = new Potion("Health Potion", ItemType.CONSUMABLE, Rarity.COMMON, 50); // Item de cura
      Item ironSword = new Weapon("Iron Sword", ItemType.EQUIPMENT, Rarity.RARE, 25); // Item de ataque

      Reward clearCaveReward = new Reward("Clear the cave with the goblin", 150, ironSword);
      Reward defeatSkeletonReward = new Reward("Defeat the skeleton", 200, null);

      Enemy goblin = new Goblin("Evil goblin", 50);
      Enemy skeleton = new Skeleton("Morbid skeleton", 60);

      Quest clearCave = new Quest(
          "Clear The Cave",
          "Kill the evil goblin in the cave and bring its head as proof.",
          clearCaveReward,
          goblinHead,
          goblin);

      Quest defeatSkeleton = new Quest(
          "Defeat The Skeleton",
          "Defeat the morbid skeleton in the dungeon and bring its bones as proof.",
          defeatSkeletonReward,
          skeletonBones,
          skeleton);

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

      // escolher missão
      System.out.println("\n==================================");
      System.out.println("AVAILABLE QUESTS:");
      System.out.println("[1] " + clearCave.getTitle() + " (Difficulty: Normal)");
      System.out.println("[2] " + defeatSkeleton.getTitle() + " (Difficulty: Hard)");
      System.out.print("> Choose your quest: ");

      String questChoice = scanner.nextLine();

      // Variáveis assumirão o alvo escolhido
      Enemy currentEnemy;
      Quest currentQuest;
      Item targetItem;

      if (questChoice.equals("2")) {
          currentEnemy = skeleton;
          currentQuest = defeatSkeleton;
          targetItem = skeletonBones;
      } else {
          // Se o jogador digitar 1 (ou qualquer outra coisa inválida), assume a missão 1 por padrão
          currentEnemy = goblin;
          currentQuest = clearCave;
          targetItem = goblinHead;
      }

      // Aventureiro aceita a missão que fica na lista de missões ativas
      adventurer.acceptQuest(currentQuest);

      System.out.println("\n>> A wild " + currentEnemy.getName() + " appears! Prepare for battle!");

      boolean gameRunning = true;

      // Loop principal do Jogo
      while (gameRunning && adventurer.getLife() > 0 && currentEnemy.getLife() > 0) {

        System.out.println("\n==================================");
        System.out.println(adventurer.getName() + " HP: " + adventurer.getLife());
        System.out.println(currentEnemy.getName() + " HP: " + currentEnemy.getLife());
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
            System.out.println("\n> You attack the " + currentEnemy.getName() + "!");
            currentEnemy.receiveDmg(adventurer.getDamage());

            // Se o enemy não morreu, ele revida
            if (currentEnemy.getLife() > 0) {
              adventurer.receiveDmg(currentEnemy.attack());
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
      } else if (currentEnemy.getLife() <= 0) {
        System.out.println("\n>>> YOU WON THE BATTLE! <<<");
        System.out.println("> You picked up the " + targetItem.getName() + ".");

        adventurer.getInventory().insertItem(targetItem);

        System.out.println("\n> Returning to the village...");

        adventurer.completeQuest(currentQuest);

        System.out.println("\n> Final Hero Status:");
        System.out.println("Coins: " + adventurer.getCoins());
        System.out.println("Inventory:\n" + adventurer.getInventory().toString());
      }
    }
  }
}
