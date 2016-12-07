package com.rockinghorse.keepit.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17/10/16.
 */
public class Project {
    public String id;
    public String title;
    public Boolean active;
    public String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Project(String title) {
        this.title = title;
    }

    public Project() {
    }

    public Project(String id, String title, Boolean active, String user_id) {
        this.id = id;
        this.title = title;
        this.active = active;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel(int position) {
        return "#" + (new Integer(position + 1).toString()) + " " + this.title;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("active", active);
        result.put("user_id", user_id);

        return result;
    }

}
