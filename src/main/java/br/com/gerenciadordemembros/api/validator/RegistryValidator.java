package br.com.gerenciadordemembros.api.validator;

import br.com.gerenciadordemembros.api.enums.RegistryType;

public class RegistryValidator {

    /**
     * Valida um número de registro com base no seu tipo.
     * @param type O tipo de registro (CNPJ ou NIF).
     * @param number A string com o número do registro.
     * @return true se for válido, false caso contrário.
     */
    public static boolean isValid(RegistryType type, String number) {
        if (type == null || number == null || number.isBlank()) {
            return false;
        }

        return switch (type) {
            case CNPJ -> isCnpjValid(number);
            case NIF -> isNifValid(number);
        };
    } // <-- O FECHA-CHAVES '}' ESTAVA FALTANDO AQUI

    /**
     * Lógica de validação para CNPJ (Brasil).
     */
    private static boolean isCnpjValid(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // CNPJ deve ter 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, o que é inválido
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            // Cálculo do primeiro dígito verificador
            int sum = 0;
            int weight = 5;
            for (int i = 0; i < 12; i++) {
                sum += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * weight;
                weight--;
                if (weight < 2) {
                    weight = 9;
                }
            }
            int digit1 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
            if (Integer.parseInt(String.valueOf(cnpj.charAt(12))) != digit1) {
                return false;
            }

            // Cálculo do segundo dígito verificador
            sum = 0;
            weight = 6;
            for (int i = 0; i < 13; i++) {
                sum += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * weight;
                weight--;
                if (weight < 2) {
                    weight = 9;
                }
            }
            int digit2 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
            return Integer.parseInt(String.valueOf(cnpj.charAt(13))) == digit2;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Lógica de validação para NIF (Portugal).
     */
    private static boolean isNifValid(String nif) {
        // Remove caracteres não numéricos
        nif = nif.replaceAll("[^0-9]", "");

        // NIF deve ter 9 dígitos
        if (nif.length() != 9) {
            return false;
        }

        try {
            int checkDigit = Integer.parseInt(String.valueOf(nif.charAt(8)));
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                sum += Integer.parseInt(String.valueOf(nif.charAt(i))) * (9 - i);
            }

            int calculatedDigit = 11 - (sum % 11);
            if (calculatedDigit >= 10) {
                calculatedDigit = 0;
            }

            return checkDigit == calculatedDigit;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}