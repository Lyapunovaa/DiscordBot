package discordBot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public class ChangeTemperature implements SlashCommand {


    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        //TODO Release this #3
        return null;
    }
}
