package com.example.BackendShop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="File")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String ownerName;
    @Column()
    private String name;

    @Column()
    private String permissionWrite;

    @Column
    private String permissionRead;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name="file_id")
    private List<Photo> photos;

    @Column(name = "user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPermissionWrite() {
        return permissionWrite;
    }

    public void setPermissionWrite(String permissionWrite) {
        this.permissionWrite = permissionWrite;
    }

    public String getPermissionRead() {
        return permissionRead;
    }

    public void setPermissionRead(String permissionRead) {
        this.permissionRead = permissionRead;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
