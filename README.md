# Documentação Técnica: Sistema de Aventura RPG

Esta documentação detalha a implementação da arquitetura refatorada do sistema de aventura solo.

---

## 1. Arquitetura Base e Enums

Os enumeradores fundamentais foram mantidos para padronizar os estados e tipos no universo do jogo:
*   **`ItemType`**: Define os tipos de itens disponíveis no universo do jogo.
*   **`Rarity`**: Representa a escala de poder e escassez dos itens do jogo.
*   **`QuestState`**: Controla a máquina de estados que governa a execução e as transições das missões (`AVAILABLE`, `IN_PROGRESS`, `COMPLETED`).

---

## 2. Hierarquia de Entidades (`entities`)

A estrutura de personagens foi reformulada para evitar redundância e promover o polimorfismo.

### Classe Abstrata `Entity`
Atua como o supertipo de qualquer ser vivo no jogo. Centraliza atributos comuns (nome, vida, dano, moedas e inventário) e métodos de modificação de estado (como `receiveDmg` e `heal`). Garante o encapsulamento, impedindo, por exemplo, que a vida fique negativa.

### Subclasses de `Entity`
*   **`Adventurer`**: Representa a entidade do jogador principal. Agora utiliza os métodos e atributos herdados de `Entity`. Interage com missões (`Quest`) e gerencia um conjunto de missões ativas (`Set<Quest>`).
*   **`Enemy`**: Classe abstrata que herda de `Entity`, forçando a implementação do método `attack()` por meio de polimorfismo.
    *   **`Goblin` e `Skeleton`**: Subclasses concretas de `Enemy` que implementam seus próprios comportamentos e cálculos de dano no método de ataque.

---

## 3. Hierarquia de Equipamentos e Interfaces (`equipment`)

A estrutura de itens deixou de ser uma classe final imutável para se tornar uma arquitetura extensível.

### Classe Abstrata `Item`
Define os atributos base de qualquer item (nome, tipo, raridade). Implementa obrigatoriamente os métodos `equals()` e `hashCode()` baseados no nome do item, permitindo o correto funcionamento dentro das coleções do Java.

### Subclasses de `Item`
*   **`Weapon`**: Equipamentos que provêm atributos de combate.
*   **`Collectible`**: Itens de progressão (ex: "Goblin's Head") que não possuem ação direta, servindo puramente à herança.
*   **`Potion`**: Implementa a interface `Usavel`, permitindo restaurar os atributos de uma entidade.

### Interface `Usavel`
Define o contrato `void usar(Entity entidade)`. Garante que apenas itens consumíveis possam interagir com o herói durante o loop de combate, disparando ações via casting polimórfico (`instanceof Usavel`).

---

## 4. Sistema de Inventário Genérico e Coleções

A classe `Inventory` passou a adotar *Generics* e mapas.

### Classe `Inventory<T>`
*   Em vez de manter uma coleção genérica onde os itens existem de forma independente em uma lista simples, agora utiliza um `Map<T, Integer>` (HashMap).
*   **Stacking (Acúmulo):** Permite que itens com o mesmo `hashCode`/`equals` sejam agrupados no mesmo slot de memória, incrementando apenas o seu valor inteiro (quantidade).
*   Trata automaticamente inserções e remoções baseadas na quantidade atual do slot.

---

## 5. Missões e Recompensas (`quests`)

### Classe `Quest` e `Reward`
*   A classe `Reward` representa a recompensa que um aventureiro obtém ao concluir um desafio e possui uma agregação com um item de recompensa.
*   A classe `Quest` representa missões que um aventureiro pode obter e completar, mantendo associação com um item requisito e com recompensas. Na versão refatorada, foi adicionado um alvo específico do tipo `Enemy`.
*   A classe `Quest` agora possui `equals()` e `hashCode()` baseados em seu título, o que permite ao `Adventurer` armazená-las em um `HashSet<Quest>`. Isso elimina a possibilidade de duplicação de missões ativas e agiliza a busca.

---

## 6. Tratamento de Exceções Customizadas

*   **`ItemNotFoundException`**: Criada para modularizar erros de negócio, como a tentativa de usar um item não existente no inventário.


---

## 7. Parte principal (`Main`)

A classe principal foi modificado para atender aos requisitos, que são o try-with-resource, instanciação das novas classes, uso das novas estruturas do inventário e de aventureiro, etc.
*   **`try-with-resources`**: Implementado na instância do `Scanner` para garantir o fechamento automático da conexão com o *System.in*, evitando vazamentos de memória (memory leaks).
*   **Tratamento de Entrada (`try-catch`)**: O motor de combate lida com `NumberFormatException` (entradas inválidas no menu numérico) e captura graciosamente as exceções customizadas lançadas pelo sistema de inventário.
*   **Combate Dinâmico**: Aciona de forma cíclica o dano entre o `Adventurer` e o `Enemy`, resolvendo o fim da partida com base nos estados vitais dos objetos encadeados.