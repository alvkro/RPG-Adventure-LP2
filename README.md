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

Controla a máquina de estados que governa a execução e as transições das missões (Quests).

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

A classe `Item` foi modelada como um **Objeto Imutável (Value Object)**. Seus objetos existem de forma independente e participam de relações de **Agregação** com `Inventory`, `Quest` e `Reward`.

```java
public final class Item {
    private final String name;
    private final ItemType type;
    private final Rarity rarity;

    public Item(String name, ItemType type, Rarity rarity) {
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

A classe `Reward` representa a recompensa que um aventureiro obtém ao concluir um desafio. Possui uma **Agregação** com `Item` através do atributo `itemReward`.

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

A classe `Quest` representa missões que um aventureiro pode obter e completar. Demonstra:
* **Agregação** com `Item` (`requiredItem`).
* **Associação** com `Reward`.
* **Associação** com `Inventory`, recebido via parâmetro no método `completeQuest`.

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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Reward getReward() {
        return reward;
    }

    public Item getRequiredItem() {
        return requiredItem;
    }

    public QuestState getQuestState() {
        return questState;
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

A classe `Inventory` gerencia os itens carregados. Demonstra:
* **Composição** faz parte integral do ciclo de vida de `Adventurer`.
* **Agregação** mantém a coleção `ArrayList<Item>`, onde os itens existem de forma independente.

```java
import java.util.ArrayList;

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

---

### Classe `Adventurer`

A classe `Adventurer` representa a entidade do jogador. Demonstra:
* **Composição:** instancia e gerencia seu próprio `Inventory` no construtor.
* **Associação:** interage com `Quest` e `Reward` por meio de seus métodos de execução de missões e recebimento de recompensas.

```java
public class Adventurer {
    private String name;
    private int energy;
    private int level;
    private int coins;
    private Inventory inventory;

    public Adventurer(String name, int coins) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Adventurer name cannot be empty or null.");
        }
        if (coins < 0) {
            throw new IllegalArgumentException("Moedas não podem ser negativas.");
        }

        this.name = name;
        this.coins = coins;
        this.level = 1;
        this.energy = 100;

        this.inventory = new Inventory();
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

    public void receiveReward(Reward r) {
        if (r.getItemReward() != null) {
            this.inventory.insertItem(r.getItemReward());
        }
        this.coins += r.getCoin();
    }

    public void completeQuest(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("Mission Invalid.");
        }

        Reward reward = quest.completeQuest(this.inventory);

        if (reward != null) {
            this.receiveReward(reward);
        }
    }
}

```
