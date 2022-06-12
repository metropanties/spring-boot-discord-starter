package me.metropanties.springdiscordstarter.command;

import me.metropanties.springdiscordstarter.command.object.SlashCommandObject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public interface CommandManager {

    Optional<SlashCommandObject> getSlashCommandByName(@Nonnull String name);

    Collection<SlashCommandObject> getSlashCommands();

}
