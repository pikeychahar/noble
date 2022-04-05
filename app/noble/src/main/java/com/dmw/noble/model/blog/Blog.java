package com.dmw.noble.model.blog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blog {

@SerializedName("blog_title")
@Expose
private String blogTitle;
@SerializedName("image")
@Expose
private String image;
@SerializedName("blog_url")
@Expose
private String blogUrl;
@SerializedName("id")
@Expose
private String id;

public String getBlogTitle() {
return blogTitle;
}

public void setBlogTitle(String blogTitle) {
this.blogTitle = blogTitle;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getBlogUrl() {
return blogUrl;
}

public void setBlogUrl(String blogUrl) {
this.blogUrl = blogUrl;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}