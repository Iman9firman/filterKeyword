package com.filter.keyword.Controller;

import com.filter.keyword.DAO.VoiceLogDao;
import com.filter.keyword.Entity.VoiceLog;
import com.filter.keyword.Service.VoiceLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class Controller {
    @Autowired
    private VoiceLogDao dao;

    @Autowired
    VoiceLogService service;


    @GetMapping(value = "/")
    public String home() {
        List<String> list = dao.findVoiceLogAll();
        System.out.println(list.size() + list.toString());

        service.looping(list);

        return "";
    }

    @PostMapping(value = "/filter")
    public String filter(@RequestBody VoiceLog voiceLog) {
        System.out.println(voiceLog.toString());
        String text = voiceLog.getText_voice();

        String result = service.filterKeyword(text);

        return result;
    }


}
