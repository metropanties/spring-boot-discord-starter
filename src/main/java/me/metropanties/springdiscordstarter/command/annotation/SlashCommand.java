package me.metropanties.springdiscordstarter.command.annotation;

import net.dv8tion.jda.api.Permission;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface SlashCommand {

    String name();

    String description();

    long[] enabledGuilds() default {};

    SlashCommandOption[] options() default {};

    SubCommand[] subCommands() default {};

    Permission[] permission() default {};

}
