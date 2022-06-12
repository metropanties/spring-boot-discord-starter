package me.metropanties.springdiscordstarter.discord;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables the {@link me.metropanties.springdiscordstarter.discord.JDAConfigurationProvider} for creation of JDA beans.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan("me.metropanties.springdiscordstarter.discord")
@Import(JDAConfigurationProvider.class)
public @interface EnableDiscord {

}
