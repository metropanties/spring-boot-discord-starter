package me.metropanties.springdiscordstarter.discord.impl;

import me.metropanties.springdiscordstarter.discord.JDAConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

/**
 * Creates an JDA instance using JDA's {@link net.dv8tion.jda.api.JDABuilder#createDefault(String)}.
 */
@Configuration
public class DefaultJDAConfiguration implements JDAConfiguration {

    @Override
    public JDA jda(@NotNull String token) throws LoginException, InterruptedException {
        return JDABuilder.createDefault(token)
                .build();
    }

}
