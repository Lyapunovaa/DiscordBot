package discordBot.utils;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discordBot.commands.MakeArtCommand;
import discordBot.commands.PingCommand;
import discordBot.commands.RequestToGPTCommand;
import discordBot.commands.UpdateTokenCommand;

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
            "Запросы в текстовый GPT",
            List.of(
                    ApplicationCommandOptionData.builder()
                            .name("message")
                            .description("Твой запрос в GPT")
                            .type(ApplicationCommandOption.Type.STRING.getValue())
                            .required(true)
                            .build()
            )
    ),
    ART(
            "makeart",
            new MakeArtCommand(),
            "Делает картинку по запросу",
            List.of(
                    ApplicationCommandOptionData.builder()
                            .name("message")
                            .description("Твой запрос для создания картинки")
                            .type(ApplicationCommandOption.Type.STRING.getValue())
                            .required(true)
                            .build()
            )
    ),
    UPDATE_TOKEN(
            "updatetoken",
            new UpdateTokenCommand(),
            "basic ping command heh",
            List.of()
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
