package com.jyang.boardserver.controller;

import static com.jyang.boardserver.aop.LoginCheck.UserType.ADMIN;

import com.jyang.boardserver.aop.LoginCheck;
import com.jyang.boardserver.dto.CategoryDTO;
import com.jyang.boardserver.dto.CategoryDTO.SortStatus;
import com.jyang.boardserver.dto.request.CategoryRequest;
import com.jyang.boardserver.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = ADMIN)
    public void updateCategory(String accountId,
                               @PathVariable(name = "categoryId") int categoryId,
                               @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);

    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = ADMIN)
    public void deleteCategory(String accountId,
                               @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
    }

}
