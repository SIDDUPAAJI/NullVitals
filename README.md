🌑 Null Vitals: A 2D Horror Survival Adventure
Null Vitals is a strategically-driven 2D horror game developed in Java. Set in a derelict research facility, players must navigate a viral outbreak using limited resources, environmental puzzle-solving, and a unique light-management mechanic.

🚀 Key Technical Features
Advanced State Management: Implemented a centralized Game State Manager to decouple game logic, allowing for fluid transitions between the title screen, active gameplay, pause menus, and cinematic cutscenes.

Polymorphic Enemy AI: Utilized Object-Oriented principles to design an Enemy base class with polymorphic behaviors. Different zombie types react uniquely to player proximity and light levels.

Dynamic Torch Mechanic: Developed a visibility system where light usage is a tactical trade-off; the torch illuminates puzzles but alters enemy detection patterns and behavior.

Data Persistence & Serialization: Built a custom Save/Load system using Java File Serialization, successfully persisting complex game data including inventory arrays, puzzle states, and unlocked coordinates.

Modular Architecture: Designed with a strict focus on Separation of Concerns, ensuring that Player, Item, and InteractiveObject classes are modular and easily extensible for future content.

🎮 Gameplay Mechanics
Resource Scarcity: Combat emphasizes survival over "brute force." Players must manage health (Hearts) and utilize strategically placed healing statues.

Environmental Storytelling: Narrative progression through a "Show, Don't Tell" approach using audio logs, notes, and cryptic interactions with the mysterious Dr. Cure.

Strategic Combat: Players must evaluate when to engage hostile creatures and when to utilize key items or stealth to bypass threats.

Boss Encounter: A final challenge that tests the player's mastery of all mechanics—combat, movement, and resource management.

🛠️ Installation & Setup (For Developers)
Prerequisites: Ensure you have the Java Development Kit (JDK) installed.

Clone the Repo:

Bash

git clone https://github.com/yourusername/null-vitals.git
Open in Eclipse:

File > Import > Existing Projects into Workspace.

Select the Null Vitals folder.

Run: Locate Main.java (or your entry point), right-click, and select Run As > Java Application.

📂 Project Structure
Plaintext

├── src
│   ├── core          # Game State Manager & Main Loop
│   ├── entities      # Player, Enemy (Polymorphism), NPC
│   ├── objects       # Items, Interactive Objects, Weapons
│   ├── systems       # Save/Load (Serialization), Inventory, Lighting
│   └── ui            # HUD (Hearts), Menus, Cutscenes
└── assets            # Sprites, Audio Logs, Game Notes
