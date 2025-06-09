package com.example.gestacao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TestesCalculadoraIdadeGestacional {

    private CalculadoraIdadeGestacional calculadora = new CalculadoraIdadeGestacional();

    // Helper method to create a Date object from year, month, day
    private Date createDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void testBetaHcgNegative() {
        // Teste para beta-hCG negativo (< 25 mIU/ml)
        Date dumRecente = createDate(2025, 5, 18); // DUM para 3 semanas (menos de 4 semanas)
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso. Se a DUM for recente (menos de 4 semanas), existe possibilidade de falso negativo, sendo recomendado repetir o exame em 1 semana.", calculadora.calcularIdadeGestacional(24.9, dumRecente));
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso. Se a DUM for recente (menos de 4 semanas), existe possibilidade de falso negativo, sendo recomendado repetir o exame em 1 semana.", calculadora.calcularIdadeGestacional(0, dumRecente));

        Date dumNaoRecente = createDate(2025, 3, 1); // DUM para > 4 semanas
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso.", calculadora.calcularIdadeGestacional(24.9, dumNaoRecente));
    }

    @Test
    void testBetaHcg3Semanas() {
        // Teste para 3 semanas de gestação (5 a 50 mIU/ml)
        Date dum = createDate(2025, 5, 18); // DUM para 3 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(25, dum)); 
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(50, dum));
        // Se betaHcg < 25, deve retornar a mensagem de negativo, conforme a lógica da CalculadoraIdadeGestacional
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso. Se a DUM for recente (menos de 4 semanas), existe possibilidade de falso negativo, sendo recomendado repetir o exame em 1 semana.", calculadora.calcularIdadeGestacional(24.9, dum)); 
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(50.1, dum));
    }

    @Test
    void testBetaHcg4Semanas() {
        // Teste para 4 semanas de gestação (5 a 426 mIU/ml)
        Date dum = createDate(2025, 5, 11); // DUM para 4 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(25, dum)); 
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(426, dum));
        // Se betaHcg < 25, deve retornar a mensagem de negativo, conforme a lógica da CalculadoraIdadeGestacional
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso.", calculadora.calcularIdadeGestacional(24.9, dum)); 
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(426.1, dum));
    }

    @Test
    void testBetaHcg5Semanas() {
        // Teste para 5 semanas de gestação (18 a 7.340 mIU/ml)
        Date dum = createDate(2025, 5, 4); // DUM para 5 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(25, dum)); 
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(7340, dum));
        // Se betaHcg < 25, deve retornar a mensagem de negativo, conforme a lógica da CalculadoraIdadeGestacional
        assertEquals("Resultado negativo (<25 mUI/mL): indica que, provavelmente, não há gestação em curso.", calculadora.calcularIdadeGestacional(24.9, dum)); 
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(7340.1, dum));
    }

    @Test
    void testBetaHcg6Semanas() {
        // Teste para 6 semanas de gestação (1.080 a 56.500 mIU/ml)
        Date dum = createDate(2025, 4, 27); // DUM para 6 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(1080, dum));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(56500, dum));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(1079.9, dum));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(56500.1, dum));
    }

    @Test
    void testBetaHcg7a8Semanas() {
        // Teste para 7 a 8 semanas de gestação (7.650 a 229.000 mIU/ml)
        Date dum7 = createDate(2025, 4, 20); // DUM para 7 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(7650, dum7));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(229000, dum7));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(7649.9, dum7));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(229000.1, dum7));

        Date dum8 = createDate(2025, 4, 13); // DUM para 8 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(7650, dum8));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(229000, dum8));
    }

    @Test
    void testBetaHcg9a12Semanas() {
        // Teste para 9 a 12 semanas de gestação (25.700 a 288.000 mIU/ml)
        Date dum9 = createDate(2025, 4, 6); // DUM para 9 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(25700, dum9));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(288000, dum9));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(25699.9, dum9));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(288000.1, dum9));

        Date dum12 = createDate(2025, 3, 16); // DUM para 12 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(25700, dum12));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(288000, dum12));
    }

    @Test
    void testBetaHcg13a16Semanas() {
        // Teste para 13 a 16 semanas de gestação (13.300 a 254.000 mIU/ml)
        Date dum13 = createDate(2025, 3, 9); // DUM para 13 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(13300, dum13));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(254000, dum13));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(13299.9, dum13));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(254000.1, dum13));

        Date dum16 = createDate(2025, 2, 16); // DUM para 16 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(13300, dum16));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(254000, dum16));
    }

    @Test
    void testBetaHcg17a24Semanas() {
        // Teste para 17 a 24 semanas de gestação (4.060 a 165.500 mIU/ml)
        Date dum17 = createDate(2025, 2, 9); // DUM para 17 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(4060, dum17));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(165500, dum17));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(4059.9, dum17));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(165500.1, dum17));

        Date dum24 = createDate(2024, 12, 22); // DUM para 24 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(4060, dum24));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(165500, dum24));
    }

    @Test
    void testBetaHcg25a40Semanas() {
        // Teste para 25 a 40 semanas de gestação (3.640 a 117.000 mIU/ml)
        Date dum25 = createDate(2024, 12, 15); // DUM para 25 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(3640, dum25));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(117000, dum25));
        assertEquals("Resultado positivo abaixo do esperado: pode indicar problemas na evolução da gravidez ou datação incorreta; é necessário acompanhamento médico.", calculadora.calcularIdadeGestacional(3639.9, dum25));
        assertEquals("Resultado positivo acima do esperado: pode sugerir uma gestação gemelar ou erro de datação; também deve ser avaliado por um profissional de saúde.", calculadora.calcularIdadeGestacional(117000.1, dum25));

        Date dum40 = createDate(2024, 8, 31); // DUM para 40 semanas
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(3640, dum40));
        assertEquals("Resultado positivo compatível com a idade gestacional: o valor de hCG está dentro do intervalo esperado para o número de semanas desde a DUM.", calculadora.calcularIdadeGestacional(117000, dum40));
    }

    @Test
    void testBetaHcgOutOfRange() {
        // Teste para DUM que resulta em idade gestacional fora do intervalo de 3 a 40 semanas
        Date dumMuitoAntiga = createDate(2024, 1, 1); // DUM para > 40 semanas (74 semanas)
        assertEquals("Idade gestacional calculada (74 semanas) fora do intervalo de interpretação da calculadora (3 a 40 semanas).", calculadora.calcularIdadeGestacional(10000, dumMuitoAntiga));

        Date dumMuitoRecente = createDate(2025, 6, 5); // DUM para < 3 semanas (0 semanas)
        assertEquals("Idade gestacional calculada (0 semanas) fora do intervalo de interpretação da calculadora (3 a 40 semanas).", calculadora.calcularIdadeGestacional(100, dumMuitoRecente));
    }
}


