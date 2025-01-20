package com.cursospring.arquiteturaspring;

import com.cursospring.arquiteturaspring.todos.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoAcessoEmail {

    private AppProperties properties;

    @Autowired
    public ConfiguracaoAcessoEmail(AppProperties properties) {
        this.properties = properties;
    }

    //@Bean
    public MailSender mailSender() {
        return null;
    }
}
