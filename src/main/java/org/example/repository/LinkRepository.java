package org.example.repository;

import org.example.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    // Поиск по короткому коду
    Optional<Link> findByShortCode(String shortCode);

    // Проверка существования короткого кода
    boolean existsByShortCode(String shortCode);
}