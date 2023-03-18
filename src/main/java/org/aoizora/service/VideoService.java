package org.aoizora.service;

import org.aoizora.domain.VideoEntity;
import org.aoizora.model.UniversalSearch;
import org.aoizora.model.Video;
import org.aoizora.model.VideoSearch;
import org.aoizora.repository.VideoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
public class VideoService
{
    private final VideoRepository videoRepository;
    private List<Video> videos = List.of(new Video("Test A"),
            new Video("Test B"),
            new Video("Test C"));

    public VideoService(VideoRepository videoRepository)
    {
        this.videoRepository = videoRepository;
    }

    public List<Video> getVideos()
    {
        return videos;
    }

    public Video create(Video video)
    {
        List<Video> extend = new ArrayList<>(videos);
        extend.add(video);
        videos = List.copyOf(extend);
        return video;
    }

    public List<VideoEntity> search(VideoSearch search)
    {
        if (StringUtils.hasText(search.name()) &&
            StringUtils.hasText(search.description()))
        {
            return videoRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase(
                    search.name(), search.description());
        }

        if (StringUtils.hasText(search.name()))
        {
            return videoRepository.findByNameContainsIgnoreCase(search.name());
        }

        if (StringUtils.hasText(search.description()))
        {
            return videoRepository.findByDescriptionContainsIgnoreCase(search.description());
        }

        return Collections.emptyList();
    }

    public List<VideoEntity> search(UniversalSearch search)
    {
        VideoEntity probe = new VideoEntity();

        if (StringUtils.hasText(search.value()))
        {
            probe.setName(search.value());
            probe.setDescription(search.value());
        }

        Example<VideoEntity> example = Example.of(probe,
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withStringMatcher(CONTAINING));

        return videoRepository.findAll(example);
    }
}
