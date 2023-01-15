package me.kozyarik.socksstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sock {
    private Color color;
    private Size size;
    private Integer cottonPart;

}
