package com.project.fundmanager.web;

import com.project.fundmanager.entity.FavoriteRecord;
import com.project.fundmanager.service.FavoriteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteRecordController {
    @Autowired
    FavoriteRecordService favoriteRecordService;

    @GetMapping("/getSelfFavorite")
    public Map<String,Object> getSelfFavorite(@SessionAttribute(name = "id") long uid){
        try {
            List<FavoriteRecord> res = favoriteRecordService.getByUId(uid);
            if (res == null) {
                return Map.of("error",  "Don't have any record.");
            }else {
                return Map.of("success",res);
            }
        } catch (Exception e) {
            return Map.of("error",  e.getMessage());
        }
    }

    @PostMapping("/addFavorite")
    public Map<String, Object> addFavorite(@RequestBody Map<String,String> postData, @SessionAttribute(name = "id") long uid){
        try {
            long fid = Long.parseLong(postData.get("fid"));
            int res = favoriteRecordService.addFavorite(uid,fid);
            if (res >0) {
                return Map.of("success","OK");
            }else{
                return Map.of("error",  "didn't make modification");
            }
        } catch (Exception e) {
            return Map.of("error",  e.getMessage());
        }
    }

    @PostMapping("/removeFavorite")
    public Map<String, Object> removeFavorite(@RequestBody Map<String,String> postData, @SessionAttribute(name = "id") long uid){
        try {
            long fid = Long.parseLong(postData.get("fid"));
            int res = favoriteRecordService.removeFavorite(uid,fid);
            if (res >0) {
                return Map.of("success","OK");
            }else{
                return Map.of("error",  "didn't make modification");
            }
        } catch (Exception e) {
            return Map.of("error",  e.getMessage());
        }
    }
}
