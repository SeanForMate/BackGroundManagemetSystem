package com.znh.controller;

import com.znh.model.Music;
import com.znh.serviceImpl.tools.MusicServcieImpl;
import com.znh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {

    @Autowired
    MusicServcieImpl musicServcie;

    /**
     * 上传音乐
     * @param file
     * @return
     */
    @PostMapping("/uploadMusic")
    public JsonData uploadMusic(@RequestParam("file")MultipartFile file){
        return musicServcie.uploadMusic(file);
    }

    /**
     * 添加音乐
     * @param music
     * @return
     */
    @PostMapping("/insertMusicByUserId")
    public JsonData insertMusicByUserId(Music music){
        return musicServcie.insertMusicByUserId(music);
    }

    @GetMapping("/selectMusicByUserId")
    public JsonData selectMusicByUserId(){
        return null;
    }

}
