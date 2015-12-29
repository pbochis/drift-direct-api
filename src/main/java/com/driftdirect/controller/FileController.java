package com.driftdirect.controller;

import com.driftdirect.domain.file.File;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.util.RestUrls;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
            @RequestParam(name = "height", required = false) Integer height,
            @RequestParam(name = "width", required = false) Integer width,
            @PathVariable Long id) throws IOException {
        File f = fileRepository.findOne(id);
        byte[] data = f.getData();
//        Working but not needed because polymer and android know how to resize images
        if (height != null && width != null){
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(f.getData()));
            BufferedImage rescaled = Scalr.resize(img, Scalr.Method.AUTOMATIC, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(rescaled, f.getName().split("\\.")[1], baos);
            data = baos.toByteArray();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    public static BufferedImage scale(BufferedImage src, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(src != null) {
            dbi = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(src, at);
        }
        return dbi;
    }

}
