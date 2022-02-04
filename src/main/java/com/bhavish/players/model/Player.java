package com.bhavish.players.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@ApiModel(value = "players info")
@Entity
@Table(name = "players")
public class Player {

    @ApiModelProperty(value = "player id", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ApiModelProperty(value = "player's name")
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(value = "player's age")
    @Column(name = "age", nullable = false)
    private int age;

    @ApiModelProperty(value = "created time of a player", hidden = true)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "last updated time of a player", hidden = true)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "player_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerType playerType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Team team;

    public Player() {
    }

    public Player(String name, int age, String playerType) {
        this.name = name;
        this.age = age;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.playerType = PlayerType.get(playerType);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPlayerType() {
        return this.playerType.getType();
    }

    public void setPlayerType(String playerType) {
        this.playerType = PlayerType.get(playerType);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", playerType=" + playerType +
                ", team=" + team +
                '}';
    }
}
