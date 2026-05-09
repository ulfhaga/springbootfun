package com.example.springbootfun.bdd;

import com.example.springbootfun.SpringbootfunApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringbootfunApplication.class)
@AutoConfigureMockMvc
class CucumberSpringConfiguration {
}
