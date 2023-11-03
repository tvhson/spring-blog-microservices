package com.tvhson.postservice.controller;

import com.tvhson.postservice.payload.PostDto;
import com.tvhson.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // create post
    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Long userId, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.status(201).body(postService.createPost(postDto, userId));
    }

    // get posts by user id
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostByUserId(userId));
    }

    // get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto, postId));
    }

    // delete post
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
