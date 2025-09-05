package sri.cloudshareapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sri.cloudshareapi.dto.ProfileDTO;
import sri.cloudshareapi.service.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhooks")
public class ProfileController {


    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProfile(@RequestBody ProfileDTO profileDTO){
        HttpStatus status = profileService.existsByClerkId(profileDTO.getClerkId())
                ? HttpStatus.OK : HttpStatus.CREATED;
        ProfileDTO savedProfile = profileService.createProfile(profileDTO);
        return ResponseEntity.status(status).body(savedProfile);
    }


}
