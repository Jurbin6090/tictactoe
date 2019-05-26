package com.jurbin.tictactoe.entity;

public enum Player {
    X, O, BLANK;

    @Override
    public String toString() {
        if(this.equals(BLANK))
            return "-";
        return this.name();
    }
}
