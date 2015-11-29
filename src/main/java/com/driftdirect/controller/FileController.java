package com.driftdirect.controller;

import com.driftdirect.domain.file.File;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Paul on 11/28/2015.
 */
@RestController
public class FileController {
    private FileRepository fileRepository;

    @Autowired
    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @RequestMapping(path = RestUrls.FILE, method = RequestMethod.POST)
    public ResponseEntity<Long> uploadFile(@RequestBody MultipartFile file) {
        try {
            File f = new File();
            f.setName(file.getOriginalFilename());
            f.setData(file.getBytes());
            return new ResponseEntity<Long>(fileRepository.save(f).getId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = RestUrls.FILE_ID, method = RequestMethod.GET)
    public ResponseEntity<byte[]> get(
            @RequestParam(name = "height", required = false) Long height,
            @RequestParam(name = "width", required = false) Long width,
            @PathVariable Long id) {
        System.out.println("Requested file with id" + id);
        File f = fileRepository.findOne(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(f.getData());
    }

}
