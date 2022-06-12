package me.metropanties.springdiscordstarter.command.object;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public record SubCommandObject(
        String name,
        String description,
        List<OptionData> options
) {
}
