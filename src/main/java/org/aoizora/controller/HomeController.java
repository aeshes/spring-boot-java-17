package org.aoizora.controller;

import org.aoizora.domain.VideoEntity;
import org.aoizora.model.UniversalSearch;
import org.aoizora.model.Video;
import org.aoizora.model.VideoSearch;
import org.aoizora.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class HomeController
{
    private final VideoService videoService;

    public HomeController(VideoService videoService)
    {
        this.videoService = videoService;
    }

    @GetMapping("/react")
    public String react()
    {
        return "react";
    }

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

    @PostMapping("/new-video")
    public String createVideo(@ModelAttribute Video video)
    {
        videoService.create(video);
        return "redirect:/";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute VideoSearch search, Model model)
    {
        List<VideoEntity> searchResults = videoService.search(search);
        model.addAttribute("videos", searchResults);
        return "index";
    }

    @PostMapping("/universal-search")
    public String universalSearch(@ModelAttribute UniversalSearch search, Model model)
    {
        List<VideoEntity> results = videoService.search(search);
        model.addAttribute("videos", results);
        return "index";
    }
}
