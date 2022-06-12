package me.metropanties.springdiscordstarter.command.impl;

import lombok.RequiredArgsConstructor;
import me.metropanties.springdiscordstarter.command.CommandManager;
import me.metropanties.springdiscordstarter.command.object.SlashCommandBuilder;
import me.metropanties.springdiscordstarter.command.object.SlashCommandObject;
import me.metropanties.springdiscordstarter.command.object.SubCommandObject;
import me.metropanties.springdiscordstarter.command.annotation.SlashCommand;
import me.metropanties.springdiscordstarter.command.annotation.SlashCommandExecutor;
import me.metropanties.springdiscordstarter.command.annotation.SlashCommandOption;
import me.metropanties.springdiscordstarter.command.annotation.SubCommand;
import me.metropanties.springdiscordstarter.listener.JDAListener;
import me.metropanties.springdiscordstarter.utils.MemberUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Component
@RequiredArgsConstructor
@JDAListener
public class CommandManagerImpl extends ListenerAdapter implements CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManagerImpl.class);

    private final ApplicationContext context;
    private final Set<SlashCommandObject> registeredSlashCommands = new HashSet<>();

    private EmbedBuilder embedBuilder;

    @PostConstruct
    private void init() {
        Map<String, Object> commandBeans = context.getBeansWithAnnotation(SlashCommand.class);
        if (commandBeans.values().isEmpty())
            return;

        for (Object obj : commandBeans.values()) {
            if (obj == null)
                continue;

            SlashCommandObject slashCommand = createSlashCommand(obj.getClass());
            if (slashCommand == null)
                continue;

            registeredSlashCommands.add(slashCommand);
        }

        try {
            this.embedBuilder = context.getBean(EmbedBuilder.class);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.warn("No EmbedBuilder bean found, creating default embed builder.");
            this.embedBuilder = new EmbedBuilder();
        }
    }

    private SlashCommandObject createSlashCommand(@Nonnull Class<?> commandClass) {
        SlashCommandBuilder builder = new SlashCommandBuilder();
        builder.clazz(commandClass);
        SlashCommand command = commandClass.getAnnotation(SlashCommand.class);

        // Name and description
        builder.name(command.name());
        builder.description(command.description());

        // Sub commands and options
        List<SubCommandObject> subcommands = new ArrayList<>();
        for (SubCommand subCommand : command.subCommands()) {
            if (subCommand.name() == null || subCommand.description() == null)
                continue;

            List<OptionData> subCommandOptionData = Arrays.stream(subCommand.options()).map(option -> {
                if (option.type() == null || option.name() == null || option.description() == null)
                    throw new NullPointerException("Required option input is null!");

                return new OptionData(option.type(), option.name(), option.description(), option.required());
            }).toList();
            subcommands.add(new SubCommandObject(
                    subCommand.name(),
                    subCommand.description(),
                    subCommandOptionData
            ));
        }
        builder.subCommands(subcommands);

        List<OptionData> optionData = new ArrayList<>();
        for (SlashCommandOption option : command.options()) {
            if (option.type() == null || option.name() == null || option.description() == null)
                continue;

            optionData.add(new OptionData(
                    option.type(),
                    option.name(),
                    option.description(),
                    option.required())
            );
        }
        builder.options(optionData);

        // Permissions
        builder.permissions(Arrays.asList(command.permission()));

        // Command execute method
        for (Method method : commandClass.getMethods()) {
            if (method.isAnnotationPresent(SlashCommandExecutor.class)) {
                builder.executeMethod(method);
                break;
            } else {
                throw new NullPointerException("Command executor annotation not found!");
            }
        }
        return builder.build();
    }

    private void executeCommand(@Nonnull SlashCommandObject command, @Nonnull SlashCommandInteractionEvent event) {
        try {
            command.getClazz().getDeclaredConstructor().setAccessible(true);
            Object instance = command.getClazz().getDeclaredConstructor().newInstance();
            command.getExecuteMethod().invoke(instance, event);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            LOGGER.error("An error occurred when executing command!", e);
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Guild guild = event.getGuild();
        if (guild.getIdLong() == 776219284237582377L) {
            CommandListUpdateAction updateAction = guild.updateCommands();
            List<SlashCommandData> commands = registeredSlashCommands.stream()
                    .map(SlashCommandObject::getCommandData)
                    .filter(Objects::nonNull)
                    .toList();
            updateAction.addCommands(commands).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        if (guild == null || member == null)
            return;

        String name = event.getName();
        Optional<SlashCommandObject> slashCommand = getSlashCommandByName(name);
        slashCommand.ifPresent(command -> {
            if (!MemberUtils.hasPermissions(member, command.getPermissions())) {
                event.replyEmbeds(embedBuilder.setDescription("You are missing required permissions!").build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            executeCommand(command, event);
        });
    }

    @Override
    public Optional<SlashCommandObject> getSlashCommandByName(@NotNull String name) {
        return registeredSlashCommands.stream()
                .filter(command -> command.getName().equals(name))
                .findFirst();
    }

    @Override
    public Collection<SlashCommandObject> getSlashCommands() {
        return registeredSlashCommands.stream()
                .toList();
    }

}
