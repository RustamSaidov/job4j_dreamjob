package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate implements Serializable {
    private int id;
    private String name;
    private String desc;
    private LocalDateTime date;
    private boolean visible;
    private City city;
    private byte[] photo;

    private Candidate() {
    }

    public Candidate(int id, String name, String desc, LocalDateTime date, boolean visible, City city, byte[] photo) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.visible = visible;
        this.city = city;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return getId() == candidate.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
