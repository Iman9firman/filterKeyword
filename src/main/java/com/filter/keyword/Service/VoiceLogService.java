package com.filter.keyword.Service;

import com.filter.keyword.DAO.VoiceLogDao;
import com.filter.keyword.Entity.VoiceLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VoiceLogService {
    @Autowired
    private VoiceLogDao dao;

    @Async
    public void looping(List<String> list){
        boolean keyword1 = false;
        boolean keyword2 = false;
        int i = 1 ;
        for(String q : list) {
            System.out.println(i);

            //Spilt Berdasarkan Huruf Besar dan Spasi
            String[] r = q.split("(?=\\p{Upper})|\\s");
            log.info(q +" | "+r.length);
            for (String arr : r){
                arr = arr.toLowerCase();
                if (condition("tidak", arr) || condition("putar", arr)){
                    log.info("valid 1");
                    keyword1 = true;
                }
                if (condition("terdaftar", arr) || condition("salah", arr)){
                    log.info("valid 2");
                    keyword2 = true;
                }
            }
            if (keyword1 && keyword2){
                log.info("id valid");
            }else {
                log.info(i+" tidak valid");
            }
            System.out.println();
            i+=1;
        }
    }

    public String filterKeyword(String text){
        boolean keyword1 = false;
        boolean keyword2 = false;

        //Spilt Berdasarkan Huruf Besar dan Spasi
        String[] r = text.split("(?=\\p{Upper})|\\s");
        for (String arr : r){
            arr = arr.toLowerCase();
            if (condition("tidak", arr) || condition("putar", arr)){
                keyword1 = true;
            }
            if (condition("terdaftar", arr) || condition("salah", arr)){
                keyword2 = true;
            }
        }
        if (keyword1 && keyword2){
            return "Valid";
        }else {
            return "tidak Valid";
        }
    }

    private boolean condition(String a, String b){
        int threshold = 2;
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(a, b);
        if (distance <= threshold ){
            log.info(a + " | " + b + " : " + distance);
            return true;
        }
        return false;
    }

}
