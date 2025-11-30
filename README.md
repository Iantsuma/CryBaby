# CryBaby

<img width="766" height="572" alt="image" src="https://github.com/user-attachments/assets/16dae0ad-b37c-4444-a4b9-bd8d03951ccf" />


Jogo feito em java puro como projeto.

## 🎮 Funcionalidades

Multiplayer Local: Controle simultâneo para dois jogadores no mesmo teclado.

Física de Projéteis: Sistema de tiro com hitboxes dinâmicos e prevenção de fogo amigo (autoridade de projétil).

Terreno Estratégico:

🧱 Paredes: Bloqueiam movimento e tiros.

🌊 Água: Bloqueia movimento (não pode andar), mas permite que tiros passem por cima.

Game Loop Customizado: Renderização estável a 60 FPS com buffer duplo (Double Buffering) para evitar flickering.

Sistema de Estados: Telas de Jogo e Game Over com reinício instantâneo.

## 🕹️ Controles

O jogador 1 se movimenta com WASD e atira com a barra de espaço.
O jogador 2 se movimenta com as setas do teclado e atira com Ctrl.

The Goob Station [docs site](https://docs.goobstation.com/) has documentation on GS14's content, engine, game design, and more. It also have lots of resources for new contributors to the project.

## 🚀 Como Executar
### Pré-requisitos
Ter o Java JDK instalado.

Uma IDE (Eclipse, IntelliJ, NetBeans) ou terminal.

### Passos
1.Clone este repositório:
2.git clone https://github.com/Iantsuma/CryBaby.git
3.Importe o projeto na sua IDE favorita.
4.Localize a classe principal (geralmente Main.java) e execute.

## 📂 Estrutura do Projeto

```text
CryBaby/
├── src/
│   ├── main/
│   │   ├── Main.java            # Ponto de entrada
│   │   ├── Panel.java           # Game Loop e Renderização
│   │   ├── KeyHandler.java      # Input do Teclado
│   │   └── CollisionDetector.java # Lógica de Colisão
│   ├── entity/
│   │   ├── Entity.java          # Classe Pai
│   │   ├── Player.java          # Lógica do Jogador
│   │   └── Projectile.java      # Lógica do Tiro
│   └── tile/
│       ├── Tile.java            # Objeto de Bloco (Chão, Parede)
│       └── TileManager.java     # Carregamento de Mapa
├── res/                         # Recursos (Imagens e Mapas)
│   ├── maps/                    # Mapas
│   ├── player/                  # Sprites do Jogador 1
│   ├── player2/                 # Sprites do Jogador 2
│   ├── projectiles/             # Sprite do Projétil
│   └── tiles/                   # Tiles (Imagens que compõe o mapa
└── README.md
