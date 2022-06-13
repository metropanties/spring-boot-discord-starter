package me.metropanties.springdiscordstarter.command;

import me.metropanties.springdiscordstarter.command.object.SlashCommandObject;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public interface CommandManager {

    void purgeCommands(@Nonnull CommandListUpdateAction updateAction);

    Optional<SlashCommandObject> getSlashCommandByName(@Nonnull String name);

    Collection<SlashCommandObject> getGlobalSlashCommands();

    Collection<SlashCommandObject> getGuildSlashCommands(long guildId);

    Collection<SlashCommandObject> getSlashCommands();

}
