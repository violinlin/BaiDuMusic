package com.whl.hp.baidumusic.bean;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class MusicInfo {
    private String songName;
    private String artistName;
    private String albumName;
    private String songPicSmall;
    private String songPicBig;
    private String songPicRadio;
    private String lrcLink;
    private String songLink;
    private long size;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSongPicSmall() {
        return songPicSmall;
    }

    public void setSongPicSmall(String songPicSmall) {
        this.songPicSmall = songPicSmall;
    }

    public String getSongPicBig() {
        return songPicBig;
    }

    public void setSongPicBig(String songPicBig) {
        this.songPicBig = songPicBig;
    }

    public String getSongPicRadio() {
        return songPicRadio;
    }

    public void setSongPicRadio(String songPicRadio) {
        this.songPicRadio = songPicRadio;
    }

    public String getLrcLink() {
        return lrcLink;
    }

    public void setLrcLink(String lrcLink) {
        this.lrcLink = lrcLink;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
