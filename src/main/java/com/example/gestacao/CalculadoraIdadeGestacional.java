package com.example.gestacao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CalculadoraIdadeGestacional {

    public String calcularIdadeGestacional(double betaHcg, Date ultimaMenstruacao) {
        // Calculate gestational weeks first to use in negative result message and out-of-range check
        LocalDate dum = ultimaMenstruacao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now(); // Current date is June 8, 2025
        long dias = ChronoUnit.DAYS.between(dum, hoje);
        int semanasGestacionais = (int) (dias / 7);

        // Handle negative case based on the MD Saúde site: <25 mIU/mL
        if (betaHcg < 25) {
            String baseMessage = "Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso.";
            if (semanasGestacionais < 4) {
                return baseMessage + " Se a DUM for recente (menos de 4 semanas), existe possibilidade de falso negativo, sendo recomendado repetir o exame em 1 semana.";
            } else {
                return baseMessage;
            }
        }

        // For betaHcg >= 25, proceed with gestational age interpretation
        // Check if gestational age is within the calculator\"s interpretation range (3 to 40 weeks)
        if (semanasGestacionais < 3 || semanasGestacionais > 40) {
            return "Idade gestacional calculada (" + semanasGestacionais + " semanas) fora do intervalo de interpretação da calculadora (3 a 40 semanas).";
        }

        double[] expectedRange = getExpectedBetaHcgRange(semanasGestacionais);

        double minExpected = expectedRange[0];
        double maxExpected = expectedRange[1];

        if (betaHcg >= minExpected && betaHcg <= maxExpected) {
            return "Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.";
        } else if (betaHcg < minExpected) {
            return "Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.";
        } else { // betaHcg > maxExpected
            return "Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.";
        }
    }

    private int calcularSemanasGestacionais(Date ultimaMenstruacao) {
        LocalDate dum = ultimaMenstruacao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now(); // Current date is June 8, 2025
        long dias = ChronoUnit.DAYS.between(dum, hoje);
        return (int) (dias / 7);
    }

    private double[] getExpectedBetaHcgRange(int semanasGestacionais) {
        if (semanasGestacionais == 3) {
            return new double[]{5, 50};
        } else if (semanasGestacionais == 4) {
            return new double[]{5, 426};
        } else if (semanasGestacionais == 5) {
            return new double[]{18, 7340};
        } else if (semanasGestacionais == 6) {
            return new double[]{1080, 56500};
        } else if (semanasGestacionais >= 7 && semanasGestacionais <= 8) {
            return new double[]{7650, 229000};
        } else if (semanasGestacionais >= 9 && semanasGestacionais <= 12) {
            return new double[]{25700, 288000};
        } else if (semanasGestacionais >= 13 && semanasGestacionais <= 16) {
            return new double[]{13300, 254000};
        } else if (semanasGestacionais >= 17 && semanasGestacionais <= 24) {
            return new double[]{4060, 165500};
        } else if (semanasGestacionais >= 25 && semanasGestacionais <= 40) {
            return new double[]{3640, 117000};
        }
        // This case should ideally not be reached if semanasGestacionais is within 3-40
        return new double[]{-1, -1}; 
    }
}


