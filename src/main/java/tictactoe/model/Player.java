package tictactoe.model;

public abstract class Player {

    private String name;
    private Symbol symbol;

    public Player(String name, Symbol symbol) {
        setName(name);
        setSymbol(symbol);
    }

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }

        this.name = name.trim();
    }

    public void setSymbol(Symbol symbol) {
        if (symbol == null || symbol == Symbol.EMPTY) {
            throw new IllegalArgumentException("Player symbol must be X or O.");
        }

        this.symbol = symbol;
    }
}