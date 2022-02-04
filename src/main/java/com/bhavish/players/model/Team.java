package com.bhavish.players.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@ApiModel(value = "team's info")
@Entity
@Table(name = "teams")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Team {

    @ApiModelProperty(value = "team id", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ApiModelProperty(value = "name of the team")
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(value = "location of the team")
    @Column(name = "location", nullable = false)
    private String location;

    @ApiModelProperty(value = "time at which the team is created", hidden = true)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "last updated time of team", hidden = true)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Team() {
    }

    public Team(String name, String location) {
        this.name = name;
        this.location = location;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
