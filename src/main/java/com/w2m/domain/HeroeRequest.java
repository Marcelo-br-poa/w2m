package com.w2m.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroeRequest {

    private Long id;

    @NotBlank(message = "El campo NAME no puede estar nulo")
    private String name;
}
