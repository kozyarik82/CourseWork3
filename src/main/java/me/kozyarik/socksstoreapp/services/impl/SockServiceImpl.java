package me.kozyarik.socksstoreapp.services.impl;

import me.kozyarik.socksstoreapp.dto.SockRequest;
import me.kozyarik.socksstoreapp.exception.InSufficientSockQuantityException;
import me.kozyarik.socksstoreapp.exception.InvalidSockException;
import me.kozyarik.socksstoreapp.model.Color;
import me.kozyarik.socksstoreapp.model.Size;
import me.kozyarik.socksstoreapp.model.Sock;
import me.kozyarik.socksstoreapp.services.SockService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SockServiceImpl implements SockService {
    private final HashMap<Sock, Integer> socks = new HashMap<>();

    private void validateRequest(SockRequest sockRequest) {
        if (sockRequest.getColor() == null || sockRequest.getSize() == null) {
            throw new InvalidSockException("Необходимо заполнить все поля!");
        }
        if (sockRequest.getQuantity() < 0) {
            throw new InvalidSockException("Количество должно быть больше нуля!");
        }
        if (sockRequest.getCottonPart() < 0 || sockRequest.getCottonPart() > 100) {
            throw new InvalidSockException("Процент содержания хлопка не должен быть ниже 0 и превышать 100%!");
        }
    }

    private Sock mapToSock(SockRequest sockRequest) {
        return new Sock(sockRequest.getColor(),sockRequest.getSize(),sockRequest.getCottonPart());
    }

    @Override
    public void addSock(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + sockRequest.getQuantity());
        } else {
            socks.put(sock, sockRequest.getQuantity());
        }
    }

    private void subtractionSockQuantity(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockRequest.getQuantity()) {
            socks.put(sock, sockQuantity - sockRequest.getQuantity());
        } else {
            throw new InSufficientSockQuantityException("Данный носок отсутствует!");
        }
    }
    @Override
    public void issueSock(SockRequest sockRequest) {
        subtractionSockQuantity(sockRequest);
    }
    @Override
    public int getSockQuantity(Color color, Size size, Integer cottonPartMin, Integer cottonPartMax) {
        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonPartMin != null && entry.getKey().getCottonPart() < cottonPartMin) {
                continue;
            }
            if (cottonPartMax != null && entry.getKey().getCottonPart() > cottonPartMax) {
                continue;
            }
            total+= entry.getValue();
        }
        return total;
    }
    @Override
    public void removeDefectSock(SockRequest sockRequest) {
        subtractionSockQuantity(sockRequest);
    }
}
