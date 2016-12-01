package ca.mindfuel.test.colton.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mindfuel.test.colton.model.Image;
import ca.mindfuel.test.colton.model.User;
import ca.mindfuel.test.colton.service.ImageRepository;

@RestController
@PreAuthorize("hasRole('USER')")
public class ImageResource {

	@Autowired
	private ImageRepository repository;

	@RequestMapping(value = "/rest/images/{filename}", method = GET)
	public ResponseEntity<byte[]> get(@PathVariable("filename") String filename, User currentUser) {
		Image image = repository.selectImageByFilenameAndUser(filename, currentUser);
		if (image == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(image.getFiledata(), HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/images/{filename}", method = PUT)
	public ResponseEntity<Void> set(@PathVariable("filename") String filename, @RequestBody byte[] data,
			User currentUser) {
		Image image = new Image();
		image.setFilename(filename);
		image.setUser(currentUser);
		image.setFiledata(data);

		repository.insertOrUpdate(image);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
