package com.hackathon.pronunciation.controller;

import com.hackathon.pronunciation.entity.UserAudio;
import com.hackathon.pronunciation.service.UserAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.script.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PronunciationController {

    @Autowired
    private UserAudioService userAudioService;

    @PostMapping(value = "/saveNonStandardPronunciation")
    public String saveNonStandardPronunciation(@RequestParam String uid, @RequestParam String name, @RequestParam MultipartFile file) throws IOException, SQLException {
        byte[] bytes = file.getBytes();
        userAudioService.saveUserAudio(uid, name, bytes);
        return "Audio file saved successfully";
    }

    @PostMapping(value = "/getNonStandardPronunciation", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getNonStandardPronunciation(@RequestParam String uid, @RequestParam String name) throws IOException, SQLException {
        byte[] audioByteArray;
        Optional<UserAudio> userAudio = userAudioService.getUserAudio(uid);
        if (userAudio.isPresent()) {
            Blob audioBlob = userAudio.get().getAudio();
            audioByteArray = audioBlob.getBytes(1, (int) audioBlob.length());
        } else {
            audioByteArray = new byte[0];
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=\"" + uid + ".mp3\"");
        return new ResponseEntity(audioByteArray, headers, HttpStatus.OK);
    }


    @PostMapping(value = "/getStandardPronunciation", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getStandardPronunciation(@RequestParam String uid, @RequestParam String name) throws IOException, ScriptException {
        //File file = new File(this.getClass().getClassLoader().getResource("AudioOutput.py").getFile());
        StringWriter writer = new StringWriter();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);
        ScriptEngine engine = manager.getEngineByName("python");
        //engine.put(ScriptEngine.ARGV, name);
//        Bindings bindings = new SimpleBindings();
//        bindings.put("mytext",name);
//        context.setBindings(bindings ,200);
        engine.eval(new FileReader("C:\\Users\\Khaja\\Desktop\\Dummy\\src\\main\\resources\\AudioOutput.py"),context);
        byte[] audioOutPut = writer.toString().getBytes("UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=\"" + uid + ".mp3\"");
        ResponseEntity<byte[]> response = new ResponseEntity(audioOutPut, headers, HttpStatus.OK);
        return response;
    }
}
