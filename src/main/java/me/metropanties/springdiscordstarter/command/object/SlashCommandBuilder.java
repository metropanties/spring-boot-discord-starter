package me.metropanties.springdiscordstarter.command.object;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unused")
public class SlashCommandBuilder {

    private Class<?> clazz;
    private String name;
    private String description;
    private List<Long> enabledGuilds;
    private List<OptionData> options;
    private List<SubCommandObject> subCommands;
    private List<Permission> permissions;
    private Method executeMethod;;

    public SlashCommandBuilder clazz(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public SlashCommandBuilder name(@Nonnull String name) {
        this.name = name;
        return this;
    }

    public SlashCommandBuilder description(@Nonnull String description) {
        this.description = description;
        return this;
    }

    public SlashCommandBuilder enabledGuilds(@Nonnull List<Long> enabledGuilds) {
        this.enabledGuilds = enabledGuilds;
        return this;
    }

    public SlashCommandBuilder options(@Nonnull List<OptionData> options) {
        this.options = options;
        return this;
    }

    public SlashCommandBuilder subCommands(@Nonnull List<SubCommandObject> subCommands) {
        this.subCommands = subCommands;
        return this;
    }

    public SlashCommandBuilder permissions(@Nonnull List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public SlashCommandBuilder executeMethod(@Nonnull Method executeMethod) {
        this.executeMethod = executeMethod;
        return this;
    }

    public SlashCommandObject build() {
        return new SlashCommandObject(clazz, name, description, enabledGuilds, options, subCommands, permissions, executeMethod);
    }

}
