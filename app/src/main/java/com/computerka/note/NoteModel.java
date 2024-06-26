package com.computerka.note;

public class NoteModel {
    private int id;
    private String title;
    private String description;

    public NoteModel(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public NoteModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public NoteModel() {
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
