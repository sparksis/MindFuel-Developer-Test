package ca.mindfuel.test.colton.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.service.ImageRepository;

@RestController
@RequestScope
@Transactional
@Secured("ROLE_USER")
public class ImageResource {

	@Autowired
	private ImageRepository repository;
	
	@Autowired
	private User currentUser;

	/**
	 * Load a previously saved image from the database
	 * @param filename the filename used when saving the image
	 * @return 200 & data if image was found 404 otherwise
	 */
	@RequestMapping(value = "/rest/images/{filename}", method = GET)
	public ResponseEntity<byte[]> get(@PathVariable("filename") String filename) {
		Optional<Image> image = repository.selectImageByFilenameAndUser(filename, currentUser);
		if (!image.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(image.get().getFiledata(), HttpStatus.OK);
	}

	/**
	 * Create or overwrite an image with a given name for a given user
	 * 
	 * @param filename the name to be used in saving to the database
	 * @param data the data to save to the database 
	 * @return 201 for success (does not distinguish between update/create)
	 */
	@RequestMapping(value = "/rest/images/{filename}", method = PUT)
	public ResponseEntity<Void> save(@PathVariable("filename") String filename, @RequestBody byte[] data) {
		Image image = repository.selectImageByFilenameAndUser(filename, currentUser).orElse(createImage(filename));
		image.setFiledata(data);

		repository.insertOrUpdate(image);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	private Image createImage(String filename) {
		Image image = new Image();
		image.setFilename(filename);
		image.setUser(currentUser);
		return image;
	}
}
