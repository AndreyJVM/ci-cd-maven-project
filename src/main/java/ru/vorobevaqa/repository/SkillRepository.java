package ru.vorobevaqa.repository;

import ru.vorobevaqa.entity.Skill;
import ru.vorobevaqa.entity.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findAllByOrderByDisplayOrderAscIdAsc();

    List<Skill> findByCategoryOrderByDisplayOrderAscIdAsc(SkillCategory category);
}