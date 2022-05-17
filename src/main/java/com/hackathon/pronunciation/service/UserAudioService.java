package com.hackathon.pronunciation.service;

import com.hackathon.pronunciation.entity.UserAudio;
import com.hackathon.pronunciation.repository.UserAudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserAudioService {
    @Autowired
    private UserAudioRepository userAudioRepository;

    public UserAudio saveUserAudio(String uid, String name, byte[] audio) throws SQLException {
        UserAudio userAudio = new UserAudio();
        Blob audioBlob = new SerialBlob(audio);
        userAudio.setUid(uid);
        userAudio.setName(name);
        userAudio.setAudio(audioBlob);
        return userAudioRepository.save(userAudio);
    }

    public Optional<UserAudio> getUserAudio(String uid) {
        return userAudioRepository.findById(uid);
    }
}
