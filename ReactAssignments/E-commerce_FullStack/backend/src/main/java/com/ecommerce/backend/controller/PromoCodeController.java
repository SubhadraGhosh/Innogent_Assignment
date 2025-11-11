package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.promocode.PromoCodeRequestDTO;
import com.ecommerce.backend.dto.promocode.PromoCodeResponseDTO;
import com.ecommerce.backend.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/promo") //  updated base path
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    // ADMIN APIs
    @PostMapping("/admin")
    public ResponseEntity<PromoCodeResponseDTO> createPromo(@Valid @RequestBody PromoCodeRequestDTO dto) {
        return ResponseEntity.ok(promoCodeService.createPromoCode(dto));
    }

    // Update by id
    @PutMapping("/admin/{id}")
    public ResponseEntity<PromoCodeResponseDTO> updatePromo(
            @PathVariable Long id,
            @Valid @RequestBody PromoCodeRequestDTO dto) {
        return ResponseEntity.ok(promoCodeService.updatePromoCode(id, dto));
    }

    //Delete by id
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deletePromo(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.noContent().build();
    }

    // Fetch All
    @GetMapping("/admin")
    public ResponseEntity<List<PromoCodeResponseDTO>> getAllPromos() {
        return ResponseEntity.ok(promoCodeService.getAllPromoCodes());
    }

    // Find by id
    @GetMapping("/admin/{id}")
    public ResponseEntity<PromoCodeResponseDTO> getPromoById(@PathVariable Long id) {
        return ResponseEntity.ok(promoCodeService.getPromoCodeById(id));
    }

    // 🔹 USER API (Checkout validation)
    @PostMapping("/validate")
    public ResponseEntity<PromoCodeResponseDTO> validatePromo(@RequestBody PromoCodeRequestDTO dto) {
        PromoCodeResponseDTO validated = promoCodeService.validatePromo(dto.getCode());
        return ResponseEntity.ok(validated);
    }
}
