package me.metropanties.springdiscordstarter.discord;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
@RequiredArgsConstructor
public class JDAConfigurationProvider {

    private final JDAConfiguration configuration;

    @Value("${discord.bot.token}")
    private String token;

    @Bean
    public JDA jda() throws LoginException, InterruptedException {
        return configuration.jda(this.token);
    }

}
