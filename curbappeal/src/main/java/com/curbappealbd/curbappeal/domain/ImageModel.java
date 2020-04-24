package com.curbappealbd.curbappeal.domain;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="image_model")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    @Column(name = "position")
    private int position;

    public ImageModel(){}

    public ImageModel(String name, String type, String url){
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public ImageModel(int position, String name, String type, String url){
        this.position = position;
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public Long getId(){

        return this.id;
    }

    public void setId(Long id){

        this.id = id;
    }

    public String getName(){

        return this.name;
    }

    public void setName(String name){

        this.name = name;
    }

    public String getType(){

        return this.type;
    }

    public void setType(String type){

        this.type = type;
    }

    public String getUrl(){

        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageModel that = (ImageModel) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url=" + url +
                '}';
    }
}