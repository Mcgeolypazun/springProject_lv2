package com.sparta.firsttask.controller;


import com.sparta.firsttask.dto.MessageDto;
import com.sparta.firsttask.dto.ProfileRequestDto;
import com.sparta.firsttask.dto.ProfileResponseDto;
import com.sparta.firsttask.service.ProfileService;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {

  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProfileById(@PathVariable Long id) {
    return profileService.getProfile(id);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateProfile(@PathVariable Long id,
      @RequestBody ProfileRequestDto requestDto) {
    try {
      ProfileResponseDto response = profileService.updateProfile(id, requestDto);
      return ResponseEntity.ok(response);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new MessageDto("Failed to update profile"));
    }
  }

}