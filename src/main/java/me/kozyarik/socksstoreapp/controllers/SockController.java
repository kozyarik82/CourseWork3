package me.kozyarik.socksstoreapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.kozyarik.socksstoreapp.dto.SockRequest;
import me.kozyarik.socksstoreapp.model.Color;
import me.kozyarik.socksstoreapp.model.Size;
import me.kozyarik.socksstoreapp.model.Sock;
import me.kozyarik.socksstoreapp.services.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sock")
@Tag(name="Носки",description = "CRUD-операции для работы с носками")
public class SockController {
    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }
    @PostMapping
    @Operation(summary = "Регистрация прихода товара на склад")
    public ResponseEntity<SockRequest> addSock(@RequestBody SockRequest sockRequest) {
        sockService.addSock(sockRequest);
        return ResponseEntity.ok(sockRequest);
    }
    @PutMapping
    @Operation(summary = "Регистрация отпуска носков со склада")
    public void issueSock(@RequestBody SockRequest sockRequest) {
        sockService.issueSock(sockRequest);
    }
    @GetMapping
    @Operation(summary = "Получение количества носков, соответствующих параметрам запроса")
    public ResponseEntity<Integer> getSockQuantity(@RequestParam(required = false, name = "color") Color color,
                               @RequestParam(required = false, name = "size") Size size,
                               @RequestParam(required = false, name = "cottonPartMin") Integer cottonPartMin,
                               @RequestParam(required = false, name = "cottonPartMax") Integer cottonPartMax) {
        return ResponseEntity.ok(sockService.getSockQuantity(color, size, cottonPartMin, cottonPartMax));
    }

    @DeleteMapping
    @Operation(summary = "Регистрация списания испорченных носков")
    public void removeDefectSock(@RequestBody SockRequest sockRequest) {
        sockService.removeDefectSock(sockRequest);
    }
}
