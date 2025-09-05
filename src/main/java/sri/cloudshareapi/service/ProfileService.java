package sri.cloudshareapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sri.cloudshareapi.document.ProfileDocument;
import sri.cloudshareapi.dto.ProfileDTO;
import sri.cloudshareapi.repository.ProfileRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final ProfileRepository profileRepository;


    public ProfileDTO createProfile(ProfileDTO profileDTO){

        //If already exists
        if(profileRepository.existsByClerkId(profileDTO.getClerkId())){
            return updateProfile(profileDTO);
        }

       ProfileDocument profile = ProfileDocument.builder()
                .clerkId(profileDTO.getClerkId())
                .email(profileDTO.getEmail())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .photoUrl(profileDTO.getPhotoUrl())
                .credits(5)
                .createdAt(Instant.now())
                .build();

        profile = profileRepository.save(profile);

        return ProfileDTO.builder()
                .id(profile.getId())
                .clerkId(profile.getClerkId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .photoUrl(profile.getPhotoUrl())
                .credits(profile.getCredits())
                .createdAt(profile.getCreatedAt())
                .build();
    }

    public ProfileDTO updateProfile(ProfileDTO profileDTO){
        ProfileDocument existingProfile = profileRepository.findByClerkId(profileDTO.getClerkId());
        if(existingProfile != null){
            //Update fields if required
            if(profileDTO.getEmail() != null && !profileDTO.getEmail().isEmpty()){
                existingProfile.setEmail(profileDTO.getEmail());
            }

            if(profileDTO.getFirstName() != null && !profileDTO.getFirstName().isEmpty()){
                existingProfile.setFirstName(profileDTO.getFirstName());
            }

            if(profileDTO.getLastName() != null && !profileDTO.getLastName().isEmpty()){
                existingProfile.setLastName(profileDTO.getLastName());
            }

            if(profileDTO.getPhotoUrl() != null && !profileDTO.getPhotoUrl().isEmpty()){
                existingProfile.setPhotoUrl(profileDTO.getPhotoUrl());
            }

            profileRepository.save(existingProfile);

            return ProfileDTO.builder()
                    .id(existingProfile.getId())
                    .clerkId(existingProfile.getClerkId())
                    .email(existingProfile.getEmail())
                    .firstName(existingProfile.getFirstName())
                    .lastName(existingProfile.getLastName())
                    .credits(existingProfile.getCredits())
                    .photoUrl(existingProfile.getPhotoUrl())
                    .build();
        }
        return null;
    }

    public boolean existsByClerkId(String clerkId){
        return profileRepository.existsByClerkId(clerkId);
    }

    public void deleteProfile(String clerkId){
        ProfileDocument existingProfile = profileRepository.findByClerkId(clerkId);
        if(existingProfile != null){
            profileRepository.delete(existingProfile);
        }
    }

    public ProfileDocument getCurrentProfile(){
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new UsernameNotFoundException("User not Authenticated");
        }
        String clerkId = SecurityContextHolder.getContext().getAuthentication().getName();
        return profileRepository.findByClerkId(clerkId);
    }
}
