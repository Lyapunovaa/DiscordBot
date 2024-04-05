package discordBot.commands;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;

import java.util.List;

public enum CommandEnum {
    PING(
            "ping",
            new PingCommand(),
            "basic ping command heh",
            List.of()
    ),
    REQUEST(
            "gpt",
            new RequestToGPTCommand(),
            "request to YaGPT cloud service with text from user",
            List.of(
                    ApplicationCommandOptionData.builder()
                            .name("message")
                            .description("Your request to Gpt")
                            .type(ApplicationCommandOption.Type.STRING.getValue())
                            .required(true)
                            .build()
            )
    );
    public String commandName;
    public SlashCommand slashCommand;
    public String description;
    public List<ApplicationCommandOptionData> agruments;

    CommandEnum(String commandName,
                SlashCommand slashCommand,
                String description,
                List<ApplicationCommandOptionData> arguments) {
        this.commandName = commandName;
        this.slashCommand = slashCommand;
        this.description = description;
        this.agruments = arguments;
    }

    public static SlashCommand of(String commandName) {
        for (CommandEnum value : values()) {
            if (value.commandName.equals(commandName)) {
                return value.slashCommand;
            }
        }
        throw new IllegalArgumentException("not registred command");
    }

}
