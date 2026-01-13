package com.CloudKeeper.CloudBalanceBackend.controller;

import com.CloudKeeper.CloudBalanceBackend.modal.CEdataDTO;
import com.CloudKeeper.CloudBalanceBackend.service.CEService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/costexplorer")
@RequiredArgsConstructor
public class CEController {

    private final CEService ceService;

    @PreAuthorize("hasAnyRole('ADMIN','READONLY','CUSTOMER')")
    @GetMapping
    public List<CEdataDTO> getTypeData(@RequestParam("type") String type, @RequestParam("start") String start, @RequestParam("end") String end) {
        return ceService.ceGroupByFilter(type, start, end);
    }
}
