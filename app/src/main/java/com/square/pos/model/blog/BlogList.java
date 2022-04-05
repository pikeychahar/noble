package com.square.pos.model.blog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogList {

@SerializedName("blogs")
@Expose
private List<Blog> blogs = null;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

public List<Blog> getBlogs() {
return blogs;
}

public void setBlogs(List<Blog> blogs) {
this.blogs = blogs;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}