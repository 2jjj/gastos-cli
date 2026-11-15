# Gastos CLI

A terminal expense tracker. Everything is stored in `gastos.csv` in the folder you run it from.

## Why

I wanted a way to log small expenses without opening a spreadsheet or signing up for an app. It takes one line per expense.

## Build and run

```bash
javac -d out src/Main.java
java -cp out Main
```

## How it works

A `Scanner` reads commands from stdin. Each line is parsed into a category, a description and a value, then appended to the CSV. The summary command groups values by category and prints the total.

## License

MIT.
