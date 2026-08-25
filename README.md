# Documentação Técnica: Estrutura Inicial do jogo

Esta documentação detalha a implementação das estruturas iniciais do sistema de aventura solo.

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

A classe `Reward` representa a recompensa que um aventureiro obtém ao concluir um desafio. Ele pode receber coins (moedas) ou um item do jogo.


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

# Outras classes aqui

