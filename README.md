# Documentação Técnica: Estrutura Inicial do jogo

Esta documentação detalha a implementação das estruturas iniciais do sistema
de aventura solo.

---

## 1. Enums

### A. `ItemType.java`

Define os tipos de itens disponíveis no universo do jogo.

```java
public enum ItemType {
    EQUIPMENT,
    CONSUMABLE,
    COLLECTIBLE
}
```

### B. `Rarity.java`

Representa a escala de poder e escassez dos itens do jogo.

```java
public enum Rarity {
    COMMON,
    RARE,
    LEGENDARY
}
```

### C. `QuestState.java`

Controla a máquina de estados que governa a execução e as transições das
missões (Quests).

```java
public enum QuestState {
    AVAILABLE,
    IN_PROGRESS,
    COMPLETED
}
```

---

## 2. Classes

### Classe `Item`

A classe `Item` foi modelada como um **Objeto Imutável (Value Object)**.

```java
public final class Item {
    private final String name;
    private final ItemType type;
    private final Rarity rarity;

    Item(String name, ItemType type, Rarity rarity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be empty or null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Item type cannot be null.");
        }
        if (rarity == null) {
            throw new IllegalArgumentException("Item rarity cannot be null.");
        }

        this.name = name;
        this.type = type;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
```

---

### Classe `Reward`

A classe `Reward` representa a recompensa que um aventureiro obtém ao concluir
um desafio. Ele pode receber coins (moedas) ou um item do jogo.

```java
public final class Reward {
    private final String description;
    private final int coin;
    private final Item itemReward;

    public Reward(String description, int coin, Item itemReward) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Reward description cannot be empty or null.");
        }
        if (coin < 0) {
            throw new IllegalArgumentException("Coin value cannot be negative.");
        }

        this.description = description;
        this.coin = coin;
        this.itemReward = itemReward;
    }

    public int getCoin() {
        return coin;
    }

    public Item getItemReward() {
        return itemReward;
    }
}
```

---

### Classe `Quest`

A classe `Quest` representa missões que um aventureiro pode obter e completar.
Uma `Quest` requer a entrega de um item e retorna recompensas ao aventureiro.

```java
public class Quest {
  private final String title;
  private final String description;
  private final Reward reward;
  private final Item requiredItem;
  private QuestState questState = QuestState.AVAILABLE;

  public Quest(String title, String description, Reward reward, Item required_item) {
    if (title == null || title.isBlank())
      throw new IllegalArgumentException("Title cannot be null or empty.");

    this.title = title;
    this.description = description;
    this.reward = reward;
    this.requiredItem = required_item;
  }

  public void obtainQuest() {
    if (this.questState != QuestState.AVAILABLE)
      throw new IllegalStateException("You cannot obtain this quest right now.");

    this.questState = QuestState.IN_PROGRESS;
  }

  public Reward completeQuest(Inventory inventory) {
    if (this.questState != QuestState.IN_PROGRESS)
      throw new IllegalStateException("You cannot complete a mission you have not obtained.");

    if (!inventory.hasItem(requiredItem))
      throw new IllegalStateException("Required item not yet obtained.");

    this.questState = QuestState.COMPLETED;
    inventory.removeItem(requiredItem);

    return reward;
  }

  public void failQuest() {
    if (this.questState != QuestState.IN_PROGRESS)
      throw new IllegalStateException("Cannot fail a mission not in progress.");

    this.questState = QuestState.FAILED;
  }

  @Override
  public String toString() {
    return "Title: " + this.title + "\nDescription: " + this.description
        + "\nReward: " + this.reward + "\nRequired item: " + this.requiredItem
        + "\nState: " + this.questState;
  }
}
```

---

### Classe `Inventory`

A classe `Inventory` representa o inventário de um aventureiro. Ela é composta
dentro da classe `Adventurer`. Possui capacidade máxima e representa a lista de
itens como uma `ArrayList<Item>`.

```java
public class Inventory {
  private final int maxCapacity = 40;
  private ArrayList<Item> items;

  public Inventory() {
    this.items = new ArrayList<>();
  }

  public void insertItem(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Item cannot be null.");

    if (items.size() < maxCapacity)
      items.add(item);
  }

  public void removeItem(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Item cannot be null.");

    if (!items.contains(item))
      throw new IllegalStateException("Item not in inventory.");

    items.remove(item);
  }

  public boolean hasItem(Item item) {
    return items.contains(item);
  }

  @Override
  public String toString() {
    String stream = "";

    for (Item item : items)
      stream += "Item name: " + item.getName() + "\n";

    return stream;
  }
}
```

### Classe `Adventurer`

A classe `Adventurer` representa a instância do jogador. Ela é composta por 
atributos base de qualquer jogador de RPG, tais como `name`, `level`, `coins` e `energy`.
[ainda vou fazer mais algumas coisas na classe...]
```
public class Adventurer {
  private String name;
  private int energy;
  private int level;
  private int coins;
  private Inventory inventory;

  public Adventurer(String name, int coins) {
    this.name = name;
    this.coins = coins;
    this.level = 1;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public int getCoins() {
    return this.coins;
  }

  public int getEnergy() {
    return this.energy;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void dialogue() {
    System.out.println("Talking...");
  }

  public void receive(Reward r) {
    this.coins += r.getCoin();
  }
}
```
