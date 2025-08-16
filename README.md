# Evolutionary Tree Analyzer (Java)

Interactive Java console application for **loading** Tree-of-Life datasets and performing **search**, **traversal**, and **subtree** queries on an evolutionary tree.

## Features
- Load nodes and links from CSV (Tree of Life format)  
- Search species by name / id  
- Pre‑order / In‑order / Post‑order traversal (writes to `data/pre-order.txt` if applicable)  
- Print a subtree from any node  
- Simple text UI (menu‑based)

## Project Structure
```
evolutionary-tree-analyzer-java/
├── src/
│   ├── Main.java
│   ├── EvolutionaryTree.java
│   └── TreeNode.java
├── data/
│   ├── treeoflife_nodes.csv
│   ├── treeoflife_links.csv
│   └── pre-order.txt          # (optional) output example
├── docs/                      # (empty by default)
└── .gitignore
```

> Note: Replace the CSVs with your own datasets if needed. Keep the same header structure expected by `EvolutionaryTree.loadNodes()` / `loadLinks()`.

## Build & Run

### Option A — Without any build tool
```bash
# from repo root
javac -encoding UTF-8 -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```

### Option B — VS Code / IntelliJ
- Create a simple Java project, add `src/` to the source path, run `Main`.
- Make sure **working directory** is the repo root so it can find `data/*.csv`.

## Usage (Menu)
```
1. Load Dataset
2. Search for species
3. Traverse Tree
4. Print Subtree
5. Exit
```
Run `1` first to load `data/treeoflife_nodes.csv` and `data/treeoflife_links.csv`, then use search/traversal features.

## Roadmap
- [ ] Basic validations for CSV headers / formats
- [ ] Export traversals to CSV/JSON
- [ ] Simple Swing/JavaFX visualization

## License
MIT
