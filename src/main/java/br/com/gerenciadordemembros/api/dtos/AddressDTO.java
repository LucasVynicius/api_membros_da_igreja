package br.com.gerenciadordemembros.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotBlank(message = "Logradouro é obrigatório")
        @Size(max = 150)
        @JsonProperty("street")
        String street,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10)
        @JsonProperty("number")
        String number,

        @Size(max = 50)
        @JsonProperty("complement")
        String complement,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 50)
        @JsonProperty("neighborhood")
        String neighborhood,

        @NotBlank(message = "Cidade é obrigatório")
        @Size(max = 50)
        @JsonProperty("city")
        String city,

        @NotBlank(message = "Estado é obrigatório")
        @Size(max = 50)
        @JsonProperty("state")
        String state,

        @NotBlank(message = "País é obrigatório")
        @Size(max = 50)
        @JsonProperty("country")
        String country,

        @NotBlank(message = "Nacionalidade é obrigátorio")
        @Size(max = 50)
        @JsonProperty("nationality")
        String nationality,

        @NotBlank(message = "CEP é obrigatório")
        @Size(max = 20)
        @JsonProperty("zipCode")
        String zipCode
) {
}
