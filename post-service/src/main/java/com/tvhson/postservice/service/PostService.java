package com.tvhson.postservice.service;

import com.tvhson.postservice.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Long userId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    PostDto getPostById(Long postId);
    List<PostDto> getPostByUserId(Long userId);
    List<PostDto> getAllPosts();
}
