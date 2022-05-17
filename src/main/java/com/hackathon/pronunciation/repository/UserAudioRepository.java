package com.hackathon.pronunciation.repository;

import com.hackathon.pronunciation.entity.UserAudio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAudioRepository extends CrudRepository<UserAudio, String> {

}
