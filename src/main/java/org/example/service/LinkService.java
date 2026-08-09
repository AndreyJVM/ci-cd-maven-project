package org.example.service;

import org.example.entity.Link;
import org.example.repository.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link createShortLink(String originalUrl) {
        String shortCode = generateUniqueCode();
        Link link = new Link(originalUrl, shortCode);
        return linkRepository.save(link);
    }

    @Transactional
    public String getOriginalUrlAndIncrement(String shortCode) {
        Link link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Link not found: " + shortCode));

        link.incrementClickCount();
        linkRepository.save(link);

        return link.getOriginalUrl();
    }

    public Link getLinkStats(String shortCode) {
        return linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Link not found: " + shortCode));
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (linkRepository.existsByShortCode(code));
        return code;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}