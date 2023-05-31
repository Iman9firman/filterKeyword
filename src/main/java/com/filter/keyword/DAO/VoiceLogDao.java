package com.filter.keyword.DAO;

import com.filter.keyword.Entity.VoiceLog;
import java.util.List;

public interface VoiceLogDao {
    List<String> findVoiceLogAll();
}
