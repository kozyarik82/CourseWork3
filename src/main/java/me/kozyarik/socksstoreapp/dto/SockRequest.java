package me.kozyarik.socksstoreapp.dto;

import lombok.Data;
import me.kozyarik.socksstoreapp.model.Color;
import me.kozyarik.socksstoreapp.model.Size;
@Data
public class SockRequest {
    private Color color;
    private Size size;
    private int cottonPart;
    private int quantity;
}
