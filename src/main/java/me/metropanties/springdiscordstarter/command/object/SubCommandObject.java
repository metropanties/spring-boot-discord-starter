package me.metropanties.springdiscordstarter.command.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class SubCommandObject {

    private final String name;
    private final String description;
    private final List<OptionData> options;

}
