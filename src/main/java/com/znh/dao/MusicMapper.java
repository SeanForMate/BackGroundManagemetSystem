package com.znh.dao;

import com.znh.model.Music;

import java.util.List;

public interface MusicMapper {
    int deleteByPrimaryKey(String musicId);

    int insert(Music record);

    int insertSelective(Music record);

    Music selectByPrimaryKey(String musicId);

    int updateByPrimaryKeySelective(Music record);

    int updateByPrimaryKey(Music record);

    List<Music> selectMusicByUserId(String musicUserId);
}