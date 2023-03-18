package org.aoizora.repository;

import org.aoizora.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long>
{
    List<VideoEntity> findByName(String name);
    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase(String name, String description);
    List<VideoEntity> findByNameContainsIgnoreCase(String name);
    List<VideoEntity> findByDescriptionContainsIgnoreCase(String description);
}
