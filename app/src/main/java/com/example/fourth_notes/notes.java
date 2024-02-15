package com.example.fourth_notes;

//creating a model class for notes to save the data
public class notes {
    //creating variables
    private String title, content,time;

    public notes(String title, String content) {
        this.title = title;
        this.content = content;

    }

    //getters and setters for all variables

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
