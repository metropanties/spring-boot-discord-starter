package me.metropanties.springdiscordstarter.command.object;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.lang.reflect.Method;
import java.util.List;

@Getter
public class SlashCommandObject {

    private final Class<?> clazz;
    private final String name;
    private final String description;
    private final List<OptionData> options;
    private final List<SubCommandObject> subCommands;
    private final List<Permission> permissions;
    private final Method executeMethod;
    private final SlashCommandData commandData;

    public SlashCommandObject(Class<?> clazz, String name, String description, List<OptionData> options, List<SubCommandObject> subCommands,
                              List<Permission> permissions, Method executeMethod) {
        this.clazz = clazz;
        this.name = name;
        this.description = description;
        this.options = options;
        this.subCommands = subCommands;
        this.permissions = permissions;
        this.executeMethod = executeMethod;

        this.commandData = Commands.slash(this.name, this.description);
        populateCommand();
    }

    private void populateCommand() {
        if (hasOptions()) {
            this.commandData.addOptions(this.options);
        }

        if (hasSubCommands()) {
            for (SubCommandObject subCommand : this.subCommands) {
                commandData.addSubcommands(new SubcommandData(subCommand.name(), subCommand.description())
                        .addOptions(subCommand.options()));
            }
        }
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    public boolean hasSubCommands() {
        return subCommands != null && !subCommands.isEmpty();
    }

}
