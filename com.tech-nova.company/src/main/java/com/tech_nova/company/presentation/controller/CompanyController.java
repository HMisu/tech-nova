package com.tech_nova.company.presentation.controller;

import com.tech_nova.company.application.dto.CompanyRequest;
import com.tech_nova.company.application.dto.CompanyResponse;
import com.tech_nova.company.application.service.CompanyService;
import com.tech_nova.company.presentation.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 업체 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<Void>> createCompany(
            @RequestBody CompanyRequest requestDto,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        ApiResponseDto<Void> response = companyService.createCompany(requestDto, token);
        return ResponseEntity.ok(response);
    }

    // 업체 수정
    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponseDto<CompanyResponse>> updateCompany(
            @PathVariable UUID companyId,
            @RequestBody CompanyRequest requestDto,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        ApiResponseDto<CompanyResponse> response = companyService.updateCompany(companyId, requestDto, token);
        return ResponseEntity.ok(response);
    }

    // 업체 삭제 (논리적 삭제)
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCompany(
            @PathVariable UUID companyId,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        ApiResponseDto<Void> response = companyService.deleteCompany(companyId, token);
        return ResponseEntity.ok(response);
    }

    // 업체 단건 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponseDto<CompanyResponse>> getCompanyById(@PathVariable UUID companyId) {
        ApiResponseDto<CompanyResponse> response = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(response);
    }

    // 허브 ID로 업체 조회
    @GetMapping("/hub/{hubId}")
    public ResponseEntity<ApiResponseDto<List<CompanyResponse>>> getCompaniesByHubId(
            @PathVariable UUID hubId) {
        ApiResponseDto<List<CompanyResponse>> response = companyService.getCompaniesByHubId(hubId);
        return ResponseEntity.ok(response);
    }

    // 검색 및 페이징 처리
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<CompanyResponse>>> searchCompanies(
            @RequestParam(required = false) String name, // 검색 조건: 이름
            @RequestParam(required = false) String type, // 검색 조건: 타입
            @RequestParam(required = false) String city, // 검색 조건: 도시
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable // 기본 페이지 크기 및 정렬 설정
    ) {
        ApiResponseDto<Page<CompanyResponse>> response = companyService.searchCompanies(name, type, city, pageable);
        return ResponseEntity.ok(response); // 200 OK와 함께 응답 반환
    }

    // Authorization 헤더에서 토큰 추출
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else if (authorizationHeader != null) {
            return authorizationHeader; // Bearer 없는 경우 토큰만 반환
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }
}
