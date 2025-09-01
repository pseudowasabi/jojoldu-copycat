package com.pseudowasabi.copycat_webservice.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloResponseDtoTest {

    @Test
    public void Lombok_기능_테스트() {

        // given
        String name = "test";
        int age = 25;

        // when
        HelloResponseDto helloResponseDto =  new HelloResponseDto(name, age);

        // then
        Assertions.assertThat(helloResponseDto.getName()).isEqualTo(name);
        Assertions.assertThat(helloResponseDto.getAge()).isEqualTo(age);
    }

    @Test
    public void Lombok_기능_테스트_JUnit만_사용() {

        // given
        String name = "test";
        int age = 25;

        // when
        HelloResponseDto helloResponseDto =  new HelloResponseDto(name, age);

        // then
        org.junit.jupiter.api.Assertions.assertEquals(helloResponseDto.getName(), name);
        org.junit.jupiter.api.Assertions.assertEquals(helloResponseDto.getAge(), age);
    }
}