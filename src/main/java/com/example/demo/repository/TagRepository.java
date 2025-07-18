package com.example.demo.repository;

import com.example.demo.entity.Tag;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
  boolean existsByName(String name);

  Tag findByName(String name);

  Set<Tag> findByIdIn(Collection<Long> ids);

  //  Set<Tag> findByNameIn(Collection<String> names);
  Set<Tag> findByNameIn(Set<String> names); // 新增
}
