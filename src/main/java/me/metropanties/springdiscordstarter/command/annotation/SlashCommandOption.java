package me.metropanties.springdiscordstarter.command.annotation;

import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SlashCommandOption {

    OptionType type();

    String name();

    String description();

    boolean required() default false;

}
