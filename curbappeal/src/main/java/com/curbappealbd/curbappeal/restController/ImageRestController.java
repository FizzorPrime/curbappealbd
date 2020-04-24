package com.curbappealbd.curbappeal.restController;

import com.cloudinary.utils.ObjectUtils;
import com.curbappealbd.curbappeal.domain.ImageModel;
import com.curbappealbd.curbappeal.repositories.ImageRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cloudinary.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@RestController
public class ImageRestController implements CommandLineRunner {

    private ImageRepository imageRepository;
    public ImageRestController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "fizzor",
            "api_key", "498357189749388",
            "api_secret", "VuE6f0qPJ_NwJMlfXSjmHLZVsXg",
            "folder", "curbappealbd"));

    // Create
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<Object> createImage(@RequestBody ImageModel image) {
        image.setPosition((int) imageRepository.count() + 1);
        Iterable<ImageModel> allImages = imageRepository.findAll();
        //ArrayList<Integer> currentPositions = new ArrayList<Integer>();
        for(ImageModel databaseImage : allImages){
            //currentPositions.add(databaseImage.getPosition());
            if (image.getPosition() <= databaseImage.getPosition()){
                image.setPosition(databaseImage.getPosition() + 1);
            }
        }
        //System.out.println(currentPositions);
//        for (int i=1; i <= imageRepository.count(); i++) {
//            if(existsIn(currentPositions, i)){
//                continue;
//            }
//            else{
//                image.setPosition(i);
//                break;
//            }
//        }
        imageRepository.save(image);
        return new ResponseEntity<>(image, HttpStatus.CREATED);
    }

    public boolean existsIn(ArrayList<Integer> list, int toCheckValue){
        for (int element : list) {
            if (element == toCheckValue) {
                return true;
            }
        }
        return false;
    }

    // Read
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/images")
    public ResponseEntity<Object> getImage() {
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/images/{id}")
    public ResponseEntity<Object> getImageById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(imageRepository.findById(id), HttpStatus.OK);
    }

    // Update
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/images/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateImage(@PathVariable("id") Long id, @RequestBody ImageModel image) {
        imageRepository.deleteById(id);
        image.setId(id);
        imageRepository.save(image);
        return new ResponseEntity<>("Image is updated successsfully", HttpStatus.OK);
    }

    // Delete
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws IOException {
        System.out.println(imageRepository.findById(id).get());
        String publicId = imageRepository.findById(id).get().getUrl();
        imageRepository.deleteById(id);
        cloudinary.uploader().destroy(publicId,
                ObjectUtils.emptyMap());
        return new ResponseEntity<>("Image is deleted successsfully", HttpStatus.OK);
    }

    @Override
    public void run(String... args) throws Exception {
        // These declarations are for attempting to store the images directly to the database
        // I will likely use them in the future for when I use this project for my client
        // image 1
//        ClassPathResource backImgFile = new ClassPathResource("image/eh_rehtian_climates.png");
//        byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
//        backImgFile.getInputStream().read(arrayPic);
//        ImageModel climates = new ImageModel(1, "Eh-rehtian-climates", "png", arrayPic);
//
//        // image 2
//        ClassPathResource blueImgFile = new ClassPathResource("image/eh_rehtian_topology.png");
//        arrayPic = new byte[(int) blueImgFile.contentLength()];
//        blueImgFile.getInputStream().read(arrayPic);
//        ImageModel topology = new ImageModel(2, "Eh-rehtian-topology", "png", arrayPic);
//
//        // image 3
//        ClassPathResource thorlakTest = new ClassPathResource("image/thorlak.jpg");
//        arrayPic = new byte[(int) thorlakTest.contentLength()];
//        thorlakTest.getInputStream().read(arrayPic);
//        ImageModel thorlak = new ImageModel(3, "Thorlak", "jpg", arrayPic);

//        byte[] fileContent = FileUtils.readFileToByteArray(new File("src/main/resources/image/eh_rehtian_climates.png"));
//        String encodedString = Base64.getEncoder().encodeToString(fileContent);
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//        ImageModel climates = new ImageModel(1, "eh_rehtian_climates", "png", decodedBytes);
//
//        fileContent = FileUtils.readFileToByteArray(new File("src/main/resources/image/eh_rehtian_topology.png"));
//        encodedString = Base64.getEncoder().encodeToString(fileContent);
//        decodedBytes = Base64.getDecoder().decode(encodedString);
//        ImageModel topology = new ImageModel(2, "eh_rehtian_topology", "png", decodedBytes);
//
//        fileContent = FileUtils.readFileToByteArray(new File("src/main/resources/image/thorlak.jpg"));
//        encodedString = Base64.getEncoder().encodeToString(fileContent);
//        decodedBytes = Base64.getDecoder().decode(encodedString);
//        ImageModel thorlak = new ImageModel(3, "thorlak", "jpg", decodedBytes);

        File toUpload = new File("src/main/resources/image/eh_rehtian_climates.png");
        Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
        ImageModel climates = new ImageModel(1, "eh_rehtian_climates", "png", uploadResult.get("url").toString());

        toUpload = new File("src/main/resources/image/eh_rehtian_topology.png");
        uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
        ImageModel topology = new ImageModel(2, "eh_rehtian_topology", "png", uploadResult.get("url").toString());

        toUpload = new File("src/main/resources/image/thorlak.jpg");
        uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
        ImageModel thorlak = new ImageModel(3, "thorlak", "jpg", uploadResult.get("url").toString());

        // store image to MySQL via SpringCRUD
        imageRepository.save(topology);
        imageRepository.save(climates);
        imageRepository.save(thorlak);
    }
}

