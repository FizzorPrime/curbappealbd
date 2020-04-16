package com.curbappealbd.curbappeal.restController;

import com.curbappealbd.curbappeal.domain.ImageModel;
import com.curbappealbd.curbappeal.repositories.ImageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageRestController implements CommandLineRunner {

    private ImageRepository imageRepository;
    public ImageRestController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Create
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<Object> createImage(@RequestBody ImageModel image) {
        image.setId(imageRepository.count() + 1);
        for (int i=1; i <= imageRepository.count(); i++) {
            if(imageRepository.existsById(Integer.toUnsignedLong(i))){
                continue;
            }
            else{
                image.setId((long) i);
                break;
            }
        }
        imageRepository.save(image);
        return new ResponseEntity<>("Image is created successfully", HttpStatus.CREATED);
    }

    // Read
    @RequestMapping(value = "/images")
    public ResponseEntity<Object> getImage() {
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    // Update
    @RequestMapping(value = "/images/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateImage(@PathVariable("id") Long id, @RequestBody ImageModel image) {
        imageRepository.deleteById(id);
        image.setId(id);
        imageRepository.save(image);
        return new ResponseEntity<>("Image is updated successsfully", HttpStatus.OK);
    }

    // Delete
    @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        imageRepository.deleteById(id);
        return new ResponseEntity<>("Image is deleted successsfully", HttpStatus.OK);
    }

    @Override
    public void run(String... args) throws Exception {
//        ImageModel honey = new ImageModel(1L, "Honey", new BigDecimal("8.95"), "https://honey.com", "1");
//        ImageModel almond = new ImageModel(2L, "Almond", new BigDecimal("12.95"), "https://almonds.com", "2");
//        imageRepository.save(honey);
//        imageRepository.save(almond);

        // image 1
        ClassPathResource backImgFile = new ClassPathResource("image/eh_rehtian_climates.png");
        byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
        backImgFile.getInputStream().read(arrayPic);
        ImageModel climates = new ImageModel(1, "Eh-rehtian-climates", "png", arrayPic);

        // image 2
        ClassPathResource blueImgFile = new ClassPathResource("image/eh_rehtian_topology.png");
        arrayPic = new byte[(int) blueImgFile.contentLength()];
        blueImgFile.getInputStream().read(arrayPic);
        ImageModel topology = new ImageModel(2, "Eh-rehtian-topology", "png", arrayPic);

        // image 3
        ClassPathResource thorlakTest = new ClassPathResource("image/thorlak.jpg");
        arrayPic = new byte[(int) thorlakTest.contentLength()];
        thorlakTest.getInputStream().read(arrayPic);
        ImageModel thorlak = new ImageModel(3, "Thorlak", "jpg", arrayPic);

        // store image to MySQL via SpringCRUD
        imageRepository.save(topology);
        imageRepository.save(climates);
        imageRepository.save(thorlak);
    }
}

