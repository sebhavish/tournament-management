package com.bhavish.players.model;

import com.bhavish.players.exception.NoSuchResourceException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

//enum class specifying type of players
public enum PlayerType {
    BATSMEN("Batsmen"),
    BOWLER("Bowler"),
    ALLROUNDER("All Rounder"),
    WICKETKEEPER("Wicket Keeper");

    private String type;

    PlayerType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @JsonCreator
    public static PlayerType get(String type) {
        return Arrays.stream(PlayerType.values())
                .filter(playerType -> playerType.type.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new NoSuchResourceException("The Player type" + type + "not found"));
    }

    @Override
    public String toString() {
        return "PlayerType{" +
                "type='" + type + '\'' +
                '}';
    }
}
