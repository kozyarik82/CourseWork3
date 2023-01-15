package me.kozyarik.socksstoreapp.services;

import me.kozyarik.socksstoreapp.dto.SockRequest;
import me.kozyarik.socksstoreapp.model.Color;
import me.kozyarik.socksstoreapp.model.Size;

public interface SockService {

    void addSock(SockRequest sockRequest);

    void issueSock(SockRequest sockRequest);

    int getSockQuantity(Color color, Size size, Integer cottonPartMin, Integer cottonPartMax);

    void removeDefectSock(SockRequest sockRequest);
}
