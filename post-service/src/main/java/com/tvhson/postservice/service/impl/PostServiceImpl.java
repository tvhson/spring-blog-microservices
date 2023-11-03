package com.tvhson.postservice.service.impl;

import com.tvhson.postservice.entity.Post;
import com.tvhson.postservice.payload.PostDto;
import com.tvhson.postservice.payload.UserDto;
import com.tvhson.postservice.repository.PostRepository;
import com.tvhson.postservice.service.APIClient;
import com.tvhson.postservice.service.PostService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final APIClient apiClient;

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefault")
    @Override
    public PostDto createPost(PostDto postDto, Long userId) {
        boolean isUserExists = apiClient.existsById(userId);
        if (!isUserExists) {
            throw new RuntimeException("User not found");
        }
        Post post = modelMapper.map(postDto, Post.class);
        post.setCreatedAt(LocalDate.now());
        post.setAuthorId(userId);
        Post savedPost = postRepository.save(post);

        PostDto newPostDto = modelMapper.map(savedPost, PostDto.class);
        newPostDto.setUser(apiClient.getUserById(userId));
        return newPostDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUser(apiClient.getUserById(post.getAuthorId()));
        return postDto;
    }

    @Override
    public List<PostDto> getPostByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByAuthorId(userId);
        return posts.stream().map(post -> {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setUser(apiClient.getUserById(userId));
            return postDto;
        }).toList();
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setUser(apiClient.getUserById(post.getAuthorId()));
            return postDto;
        }).toList();
    }

    public PostDto getDefault(PostDto postDto, Long userId, Exception exception) {
        return PostDto.builder()
                .id(0L)
                .title("Default title")
                .content("Default content")
                .user(UserDto.builder()
                        .id(0L)
                        .userName("Default username")
                        .build())
                .createdAt(LocalDate.now())
                .build();
    }
}
