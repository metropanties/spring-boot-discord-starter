package me.metropanties.springdiscordstarter.discord;

import net.dv8tion.jda.api.JDA;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public interface JDAConfiguration {

    JDA jda(@Nonnull String token) throws LoginException, InterruptedException;

}
