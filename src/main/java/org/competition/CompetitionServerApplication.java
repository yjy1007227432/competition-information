package org.competition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling//开启定时任务支持
@MapperScan("org.competition.mapper")
public class CompetitionServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompetitionServerApplication.class, args);
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory config = new MultipartConfigFactory();
        config.setMaxFileSize("1100MB");
        config.setMaxRequestSize("1100MB");
        return config.createMultipartConfig();
    }
}
