package com.znh.serviceImpl.tools;

import com.znh.dao.MusicMapper;
import com.znh.model.Music;
import com.znh.model.User;
import com.znh.util.Commons;
import com.znh.util.JsonData;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MusicServcieImpl {

    private static final Logger log = LoggerFactory.getLogger(MusicServcieImpl.class);

    @Autowired
    MusicMapper musicMapper;

    /**
     * 上传音频到后台
     * @param file
     * @return
     */
    public JsonData uploadMusic(MultipartFile file){
        if(file == null){
            return new JsonData(1,"失败",null);
        }
        JsonData jsonData = null;
        // 获取文件名字
        String name = file.getName();
        // 存储音频路径
        String path = Commons.GetStartPath()+"/static/upload/audio";
        // 判断存储音频路径是否存在，不存在就创建
        File pathFile = new File(path);
        if(!pathFile.exists()){
            // 创建存储音频路径
            pathFile.mkdirs();
        }
        // 生成一个新的音频文件
        String fileName = file.getOriginalFilename();
        // 获取音频文件的后缀名
        int index = fileName.lastIndexOf(".");
        String subfixName = UUID.randomUUID().toString().replace("-","") + fileName.substring(index,fileName.length());
        // 将音频文件写入到存储音频文件路径
        try {
            File musicFile = new File(pathFile+File.separator+subfixName);
            file.transferTo(musicFile);
            jsonData = new JsonData(0,"成功","static/upload/audio/"+subfixName);
            return jsonData;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            jsonData = new JsonData(2,"失败","上传服务器异常，请联系管理员！");
        }
        return jsonData;
    }

    /**
     * 添加音乐信息
     * @param music
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonData insertMusicByUserId(Music music){
        if(music == null){
            return new JsonData(1,"失败","参数为空");
        }
        JsonData jsonData = null;
        try{
            User user = (User)SecurityUtils.getSubject().getPrincipal();
            Music music1 = new Music(UUID.randomUUID().toString(),music.getMusicName(),music.getMusicArtist(),music.getMusicPath(),user.getUserId(),user.getRealName(),new Date());
            if(musicMapper.insertSelective(music1)>0){
                jsonData = new JsonData(0,"成功","添加成功");
            }else{
                jsonData = new JsonData(1,"失败","添加失败");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            jsonData = new JsonData(2,"失败","服务器异常，请联系管理员！");
        }
        return jsonData;
    }

    /**
     * 根据登录用户获取用户上传的音乐信息
     * @return
     */
    public JsonData selectMusicByUserId(){
        JsonData jsonData = null;
        try{
            User user = (User)SecurityUtils.getSubject().getPrincipal();
            List<Music> musicDataList = new ArrayList<Music>();
            musicDataList = musicMapper.selectMusicByUserId(user.getUserId());
            if(musicDataList.size()>0){
                jsonData = new JsonData(0,"成功",musicDataList);
            }else{
                jsonData = new JsonData(1,"失败",null);
            }
            return jsonData;
        }catch (Exception e){
            log.error(e.getMessage());
            jsonData = new JsonData(2,"失败","服务器异常，请联系管理员");
        }
        return jsonData;
    }

}
