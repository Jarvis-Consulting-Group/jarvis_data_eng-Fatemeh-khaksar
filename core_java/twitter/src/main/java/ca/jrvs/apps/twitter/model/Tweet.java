package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favorited",
        "retweeted"})

public class Tweet {
    @JsonProperty("created_id")
    public String created_at;
    @JsonProperty("id")
    public BigInteger id;
    @JsonProperty("id_str")
    public String id_str;
    @JsonProperty("text")
    public String text;
    @JsonProperty("entities")
    public Entities entities;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("retweet_count")
    private BigInteger retweet_count;
    @JsonProperty("favorite_count")
    private BigInteger favorite_count;
    @JsonProperty("favorited")
    private Boolean favorited;
    @JsonProperty("retweeted")
    private Boolean retweeted;


    public Tweet(String created_at, BigInteger id, String id_str, String text, Entities entities, Coordinates coordinates, BigInteger retweet_count, BigInteger favorite_count, Boolean favorited, Boolean retweeted) {
        this.created_at = created_at;
        this.id = id;
        this.id_str = id_str;
        this.text = text;
        this.entities = entities;
        this.coordinates = coordinates;
        this.retweet_count = retweet_count;
        this.favorite_count = favorite_count;
        this.favorited = favorited;
        this.retweeted = retweeted;
    }
    @JsonProperty("created_at")
    public String getCreated_at() {
        return created_at;
    }
    @JsonProperty("created_at")
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    @JsonProperty("id")
    public BigInteger getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(BigInteger id) {
        this.id = id;
    }
    @JsonProperty("id_str")
    public String getId_str() {
        return id_str;
    }
    @JsonProperty("id_str")
    public void setId_str(String id_str) {
        this.id_str = id_str;
    }
    @JsonProperty("text")
    public String getText() {
        return text;
    }
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }
    @JsonProperty("entities")
    public Entities getEntities() {
        return entities;
    }
    @JsonProperty("entities")
    public void setEntities(Entities entities) {
        this.entities = entities;
    }
    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }
    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    @JsonProperty("retweet_count")
    public BigInteger getRetweet_count() {
        return retweet_count;
    }
    @JsonProperty("retweet_count")
    public void setRetweet_count(BigInteger retweet_count) {
        this.retweet_count = retweet_count;
    }
    @JsonProperty("favorite_count")
    public BigInteger getFavorite_count() {
        return favorite_count;
    }
    @JsonProperty("favorite_count")
    public void setFavorite_count(BigInteger favorite_count) {
        this.favorite_count = favorite_count;
    }
    @JsonProperty("favorited")
    public Boolean getFavorited() {
        return favorited;
    }
    @JsonProperty("favorited")
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }
    @JsonProperty("retweeted")
    public Boolean getRetweeted() {
        return retweeted;
    }
    @JsonProperty("retweeted")
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", id_str='" + id_str + '\'' +
                ", text='" + text + '\'' +
                ", entities=" + entities +
                ", coordinates=" + coordinates +
                ", retweet_count=" + retweet_count +
                ", favorite_count=" + favorite_count +
                ", favorited=" + favorited +
                ", retweeted=" + retweeted +
                '}';
    }
}


