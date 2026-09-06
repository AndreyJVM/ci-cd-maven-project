package ru.vorobevaqa.controller.web;

import ru.vorobevaqa.dto.SkillDto;
import ru.vorobevaqa.service.GitHubService;
import ru.vorobevaqa.service.SkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @MockBean
    private GitHubService gitHubService;

    @Test
    @DisplayName("GET /about - should return about view with skills and categories in model")
    void shouldReturnAboutPageWithSkills() throws Exception {
        // Подготавливаем тестовые данные
        SkillDto mockSkill = SkillDto.builder()
                .id(1L)
                .name("CI/CD пайплайны")
                .categoryKey("DEVOPS_CICD")
                .categoryDisplayName("DevOps & CI/CD")
                .levelTitle("Уверенный")
                .levelPercentage(75)
                .badgeColor("bg-primary")
                .description("Тестовое описание")
                .tools(List.of("Docker", "GitHub Actions"))
                .build();

        when(skillService.getAllSkills()).thenReturn(List.of(mockSkill));

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/about"))
                .andExpect(model().attributeExists("skills"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @DisplayName("GET / - should return index view")
    void shouldReturnIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/index"));
    }

    @Test
    @DisplayName("GET /projects - should return projects view with repos in model")
    void shouldReturnProjectsPageWithRepos() throws Exception {
        when(gitHubService.getRecentRepositories()).thenReturn(List.of());

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/projects"))
                .andExpect(model().attributeExists("repos"));
    }
}