# ForgeRPG

ForgeRPG is a Minecraft server plugin that provides various commands and events to enhance the gameplay experience. It is built using the Minestom library and provides a framework for creating custom commands and events.

## Features

- Custom commands
- Custom events
- Database integration
- Player management

## Getting Started

### Prerequisites

- Java 21 or higher
- Gradle

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/TreesOnTop/ForgeRPG.git
   cd ForgeRPG
   ```

2. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

3. Run the server:
   ```sh
   ./gradlew run
   ```

## Usage

### Commands

ForgeRPG provides a set of custom commands that can be used in the game. These commands are registered automatically when the server starts. You can find the list of available commands in the `src/main/java/com/github/treesontop/commands` package.

### Events

ForgeRPG also provides custom events that can be triggered during gameplay. These events are registered automatically when the server starts. You can find the list of available events in the `src/main/java/com/github/treesontop/events` package.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue if you have any suggestions or improvements.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
