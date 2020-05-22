package com.znh.model;

import java.util.Date;

public class Music {

    private String musicId;

    private String musicName;

    private String musicArtist;

    private String musicPath;

    private String musicUserId;

    private String createPerson;

    private Date createDateTime;

    private String updatePerson;

    private Date updateDateTime;

    public Music() {
    }

    public Music(String musicId, String musicName, String musicArtist, String musicPath, String musicUserId, String createPerson,Date createDateTime) {
        this.musicId = musicId;
        this.musicName = musicName;
        this.musicArtist = musicArtist;
        this.musicPath = musicPath;
        this.musicUserId = musicUserId;
        this.createPerson = createPerson;
        this.createDateTime = createDateTime;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId == null ? null : musicId.trim();
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName == null ? null : musicName.trim();
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist == null ? null : musicArtist.trim();
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath == null ? null : musicPath.trim();
    }

    public String getMusicUserId() {
        return musicUserId;
    }

    public void setMusicUserId(String musicUserId) {
        this.musicUserId = musicUserId == null ? null : musicUserId.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson == null ? null : updatePerson.trim();
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}